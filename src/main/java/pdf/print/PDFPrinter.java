package pdf.print;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.printing.Scaling;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.io.File;
import java.io.IOException;

public class PDFPrinter {

    public static void printPDF(String filePath) {
    	StringBuilder errorMessages = new StringBuilder();
        try {
            // Load the PDF document
        	PDDocument document = PDDocument.load(new File(filePath));

            // Create a PrinterJob
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            // Lookup the default print service
            PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

            if (printService != null) {
                printerJob.setPrintService(printService);

                // Create a PDFPrintable object
                PDFPrintable printable = new PDFPrintable(document, Scaling.SHRINK_TO_FIT);

                PageFormat pageFormat = printerJob.defaultPage();
                Paper paper = pageFormat.getPaper();
                paper.setSize(595.275590551, 841.88976378);
                paper.setImageableArea(0, 0, 595.275590551, 841.88976378);
                pageFormat.setPaper(paper);
                printerJob.setPrintable(printable, pageFormat);

                // Print the document
                printerJob.print();

                document.close();
            } else {
            	errorMessages.append("No default print service found. \n");
            }
        } catch (IOException e) {
        	errorMessages.append("Error loading PDF \n");
        } catch (PrinterException e) {
        	errorMessages.append("Printing error \n");
        }
        if (errorMessages.length() > 0) {
            JOptionPane.showMessageDialog(null, 
                    errorMessages.toString(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
}
