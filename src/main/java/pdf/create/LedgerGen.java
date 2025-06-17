package pdf.create;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import database.Processes;

public class LedgerGen {
    private static String formatAmount(double value) {
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "IN"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(value);
    }
    public static void createLedger(String name, LocalDate fromDate, LocalDate toDate, DefaultTableModel tableModel) throws ClassNotFoundException, SQLException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedFromDate = fromDate.format(formatter);
            String formattedToDate = toDate.format(formatter);

            String filePath = getSaveLocation(name);
            if (filePath == null) {
                System.out.println("No file selected. Operation cancelled.");
                return;
            }

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.setMargins(20, 40, 20, 40);

            PdfFont boldFont = PdfFontFactory.createFont("Helvetica-Bold");
            PdfFont normalFont = PdfFontFactory.createFont("Helvetica");

            document.add(new Paragraph(name)
                    .setFont(boldFont)
                    .setFontSize(20)
                    .setFontColor(ColorConstants.BLACK)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(10));

            String address1 = Processes.getAddress1(name);
            String address2 = Processes.getAddress2(name);
            String address3 = Processes.getAddress3(name);
            String gst = Processes.getGST(name);

            if (isNotEmpty(address1) || isNotEmpty(address2) || isNotEmpty(address3) || isNotEmpty(gst)) {
                Paragraph addressDetails = new Paragraph()
                        .setFont(normalFont)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(10);

                if (isNotEmpty(address1)) addressDetails.add(address1 + "\n");
                if (isNotEmpty(address2)) addressDetails.add(address2 + "\n");
                if (isNotEmpty(address3)) addressDetails.add(address3 + "\n");
                if (isNotEmpty(gst)) addressDetails.add("GST No: " + gst + "\n");

                document.add(addressDetails);
            }

            String contactPerson = Processes.getCntPerson(name);
            String phoneNo = Processes.getPhoneNo(name);
            String email = Processes.getEmail(name);

            if (isNotEmpty(contactPerson) || isNotEmpty(phoneNo) || isNotEmpty(email)) {
                Paragraph contactDetails = new Paragraph()
                        .setFont(normalFont)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginBottom(10);

                if (isNotEmpty(contactPerson)) contactDetails.add("Contact Person: " + contactPerson + "\n");
                if (isNotEmpty(phoneNo)) contactDetails.add("Phone: " + phoneNo + "\n");
                if (isNotEmpty(email)) contactDetails.add("Email: " + email + "\n");

                document.add(contactDetails);
            }

            document.add(new Paragraph("From: " + formattedFromDate + "  To: " + formattedToDate)
                    .setFont(normalFont)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            Table table = new Table(UnitValue.createPercentArray(tableModel.getColumnCount()))
                    .useAllAvailableWidth()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER)
                    .setMarginTop(10)
                    .setMarginBottom(20);

            // Previous Balance row
            table.addCell(new Cell(1,6).add(new Paragraph("Previous Balance")));
            table.addCell(new Cell(1,1).add(new Paragraph(formatAmount(Double.parseDouble(Processes.getPrevAmt(name, formattedFromDate.toString()))))));

            // Header cells
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(tableModel.getColumnName(i))
                                .setFont(boldFont)
                                .setFontSize(12)
                                .setTextAlignment(TextAlignment.CENTER))
                        .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                        .setPadding(5));
            }

            // Identify Debit and Credit column indexes
            int debitCol = -1, creditCol = -1;
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                String colName = tableModel.getColumnName(i).toLowerCase();
                if (colName.contains("debit")) debitCol = i;
                if (colName.contains("credit")) creditCol = i;
            }

            // Data cells
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    Object value = tableModel.getValueAt(row, col);
                    String strValue;

                    // Format Debit and Credit values
                    if ((col == debitCol || col == creditCol) && value != null && !value.toString().trim().isEmpty()) {
                        try {
                            strValue = formatAmount(Double.parseDouble(value.toString()));
                        } catch (NumberFormatException e) {
                            strValue = value.toString(); // fallback
                        }
                    } else {
                        strValue = value == null ? "" : value.toString();
                    }

                    table.addCell(new Cell()
                            .add(new Paragraph(strValue)
                                    .setFont(normalFont)
                                    .setFontSize(10)
                                    .setTextAlignment(TextAlignment.CENTER))
                            .setPadding(5));
                }
            }

            document.add(new Paragraph("\n")); // spacing
            document.add(table);

            // Format and add outstanding amount
            double oAmt = Double.parseDouble(Processes.getOAmt(name));
            String formattedOAmt = formatAmount(oAmt);

            Paragraph totalOutstanding = new Paragraph("Total Outstanding Amount: ₹" + formattedOAmt)
                    .setFontSize(14)
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT);

            document.add(totalOutstanding);
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static String getSaveLocation(String partyName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save PDF");

        // Default file name with party name
        String defaultFileName = partyName.replaceAll("[^a-zA-Z0-9]", "_") + "_Ledger.pdf";
        fileChooser.setSelectedFile(new File(defaultFileName));

        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf"; // Ensure .pdf extension
            }
            return filePath;
        }
        return null;
    }
}