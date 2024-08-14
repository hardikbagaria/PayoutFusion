package pdf.create;

import java.io.FileNotFoundException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

public class BillGenIGST {
    public static PdfFont boldFont;
    public static PdfFont courier;

    static {
        try {
            boldFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            courier = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., log the error, use a default font, etc.)
        }
    }
    private static Cell createCell(int rowspan, int colspan, boolean isBold, String text, float fontSize, boolean borderBottom, boolean borderTop, boolean borderRight, boolean borderLeft, boolean border, TextAlignment alignment) {
        Cell cell = new Cell(rowspan, colspan).setHeight(14.44f).add(new Paragraph(text).setFontSize(fontSize).setTextAlignment(alignment));
        cell.setFont(courier);
        cell.setPaddingTop(0.1f);    // Reduce top padding
        cell.setPaddingBottom(0.1f); // Reduce bottom padding
        cell.setMarginTop(0.1f);     // Remove top margin
        cell.setMarginBottom(0.1f);
        if (isBold) {
            cell.setFont(boldFont);
        }
        if (!border) {
            cell.setBorder(null);
        } else {
            if (!borderBottom) {
                cell.setBorderBottom(null);
            }
            else {
            	cell.setBorderBottom(new SolidBorder(ColorConstants.BLACK, 2f));
            }
            if (!borderTop) {
                cell.setBorderTop(null);
            }
            else {
            	cell.setBorderTop(new SolidBorder(ColorConstants.BLACK, 2f));
            }
            if (!borderRight) {
                cell.setBorderRight(null);
            }
            else {
            	cell.setBorderRight(new SolidBorder(ColorConstants.BLACK, 2f));
            }
            if (!borderLeft) {
                cell.setBorderLeft(null);
            }
            else {
            	cell.setBorderLeft(new SolidBorder(ColorConstants.BLACK, 2f));
            }
            cell.setHorizontalAlignment(HorizontalAlignment.CENTER);
        }
        return cell;
    }
    static float cmToPoints(float cm) {
        return cm * 72 / 2.54f;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String dest = "BillGenIGST.pdf";
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        // Set margins back to your specified values
        document.setMargins(cmToPoints(0.5f), cmToPoints(0.661f), 0, cmToPoints(0.561f)); // Left, Right, Top, Bottom
        float[] columnWidths = new float[] {
        		cmToPoints(0.85f), // First column
        		cmToPoints(1.58f), // Second column
        		cmToPoints(2.45f), // Third column
        		cmToPoints(1.55f), // Fourth column
        		cmToPoints(2.1f),  // Fifth column
        		cmToPoints(1.85f), // Sixth column
        		cmToPoints(2.2f),  // Seventh column
        		cmToPoints(1.89f), // Eighth column
        		cmToPoints(1.8f),  // Ninth column
        		cmToPoints(3.4f)   // Tenth column
        };
        
        // Create the table with the specified column widths
        Table headerTable = new Table(columnWidths);
        headerTable.setWidth(UnitValue.createPercentValue(100)); // Ensures table stretches to page width
        headerTable.setFixedLayout();
        headerTable.addCell(createCell(1, 9, true, "TAX INVOICE", 11.3f, true, true, true, true, false, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 1, false, "ORIGINAL", 11.3f, true, true, true, true, false, TextAlignment.RIGHT));
        // Company details
        headerTable.addCell(createCell(1, 6,true, "SURAJ ENTERPRISES", 11.3f, true, true, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,true, "Invoice No:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,true, "Dated:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "E-12 PANCHAL NAGAR CO-OP HSG. SOC.", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,false, "12", 11.3f, false, false, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,false, "26/01/2024", 11.3f, false, false, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "ANAND NAGAR, OPP. K.T. VISION CINEMA,", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,true, "Delivery Note:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,true, "Dated:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "VASAI ROAD (W) DIST. PALGHAR 401202", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,false, "12", 11.3f, false, false, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,false, "26/01/2024", 11.3f, false, false, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "GSTIN/UIN: 27AQHPB0072E1ZE", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,true, "Mode Of Payment:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,true, "Vehicle Details:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "MOBILE NO. 9022180909", 11.3f, true, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,false, "Immediate", 11.3f, false, false, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,false, "Auto", 11.3f, false, false, true, true, true, TextAlignment.CENTER));

        // Buyer details
        headerTable.addCell(createCell(1, 2,true, "Buyer: M/s.", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,true, "", 11.3f, false, false, false, false, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,true, "Buyer's Order No:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,true, "Dated:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "Address Line1", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,false, "", 11.3f, false, false, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,false, "", 11.3f, false, false, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "Address Line2", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,true, "Dispatch Doc. No:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,true, "Destination:", 11.3f, false, true, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 6,false, "Address Line3", 11.3f, false, false, true, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 2,false, "", 11.3f, false, false, true, true, true, TextAlignment.CENTER));
        headerTable.addCell(createCell(1, 2,false, "", 11.3f, false, false, true, true, true, TextAlignment.CENTER));

        headerTable.addCell(createCell(1, 2,false, "GSTIN/UIN:", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,false, "27AQHPB0072E1ZE", 11.3f, false, false, false, false, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(5, 4,  true, "Terms of Delivery:", 11.3f, true, true, true, true, true, TextAlignment.LEFT));

        headerTable.addCell(createCell(1, 2,  false, "PAN/IT:", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,  false, "", 11.3f, false, false, false, false, true, TextAlignment.LEFT));

        headerTable.addCell(createCell(1, 2,  false, "Cont. Person:", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,  false, "", 11.3f, false, false, false, false, true, TextAlignment.LEFT));

        headerTable.addCell(createCell(1, 2,  false, "Contact:", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,  false, "", 11.3f, false, false, false, false, true, TextAlignment.LEFT));

        headerTable.addCell(createCell(1, 2,  false, "Email:", 11.3f, false, false, false, true, true, TextAlignment.LEFT));
        headerTable.addCell(createCell(1, 4,  false, "", 11.3f, false, false, false, false, true, TextAlignment.LEFT));
        

        headerTable.addCell(createCell(1, 1,  true, "SR", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 4,  true, "DESCRIPTION OF GOODS", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 1,  true, "HSN/SAC", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 1,  true, "QUANTITY", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 1,  true, "RATE", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 1,  true, "PER", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 1,  true, "AMOUNT", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  true, "NO", 10f, true, false, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        for (int i = 0; i < 12; i++) {
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1, false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        }
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4, false, "TOTAL TAXABLE AMOUNT", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1, false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  false, "IGST@18%", 11.3f, false, false, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  false, "transportation", 11.3f, false, false, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1, false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  false, "round off", 11.3f, true, false, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, false, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  false, "TOTAL", 11.3f, true, true, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "", 11.3f, true, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 9,  false, "AMOUNT CHARGEABLE (IN WORDS) :", 11.3f, false, false, false, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  false, "E. &O.E", 11.3f, false, false, true, false, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 10,  false, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));

        
        headerTable.addCell(createCell(2, 3,  true, "HSN/SAC", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(2, 2,  true, "TAXABLE VALUE", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  true, "IGST", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  true, "TOTAL", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

        
        headerTable.addCell(createCell(1, 2, true, "RATE", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 2,  true, "AMOUNT", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 1,  true, "TAX AMOUNT", 10f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

        headerTable.addCell(createCell(1, 3,  true, "", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 2,  true, "", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

        headerTable.addCell(createCell(1, 2,  true, "18%", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 2,  true, "", 10f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));

        headerTable.addCell(createCell(1, 1,  true, "", 11.3f, true, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 7,  true, "TAX AMOUNT (IN WORDS) :", 11.3f, false, true, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 3,  true, "EMAIL: hbagaria2007@gmail.com", 11.3f, false, true, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 7,  true, "", 11.3f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(6, 3,  true, "", 11.3f, true, true, true, true, true, TextAlignment.RIGHT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 7,  true, "BANK DETAILS", 11.3f, false, true, true, true, true, TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE).setUnderline());

        headerTable.addCell(createCell(1, 3,  false, "BANK NAME", 11.3f, false, true, false, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  true, ": PUNJAB NATIONAL BANK", 11.3f, false, true, true, false, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 3,  false, "A/C No.", 11.3f, false, false, false, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  true, ": 12311011001807", 11.3f, false, false, true, false, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 3,  false, "BRANCH", 11.3f, false, false, false, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  true, ": BANGUR NAGAR GOREGAON (W) ", 11.3f, false, false, true, false, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 3,  false, "IFSC CODE", 11.3f, true, false, false, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        headerTable.addCell(createCell(1, 4,  true, ": PUNB0123110 ", 11.3f, true, false, true, false, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE));
        
        headerTable.addCell(createCell(1, 5,  false, "DECLARATION:\n"
        		+ "⁕SUBJECTED TO MUMBAI JURISDICTION\n"
        		+ "⁕FOR INDUSTRIAL USE ONLY NOT FOR MEDICAL OR EDIBLE USE\n"
        		+ "⁕LATE PAYMENT WILL BE CHARGED @24% PER MONTH\n"
        		+ "⁕ GOODS ONCE SOLD WILL NOT BE TAKEN BACK", 8f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE).setHeight(72.2f));
        headerTable.addCell(createCell(1, 2,  false, "RECEIVER'S SIGN", 10f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.TOP).setHeight(72.2f));
        headerTable.addCell(createCell(1, 3,  true, "FOR SURAJ ENTERPRISES"+"\n" +"\n" +"\n" +"\n" +  "Proprietor/Authorised Sign", 10f, true, false, true, true, true, TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.TOP).setHeight(72.2f));
        
        
        document.add(headerTable);

        // Footer text
        Paragraph footer = new Paragraph("THIS IS A COMPUTER GENERATED INVOICE")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(8);
        document.add(footer);

        document.close();
    }
}
