package extras;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;

public class MakePaymentCell extends Cell {

    public MakePaymentCell(String amount, String transactionNote) {
        super(6, 3); // Set rowspan and colspan

        String upiId = "";
        String name = "";
        try {
            // Create UPI URL
            String upiUrl = "upi://pay?pa=" + upiId + "&pn=" + name + "&am=" + amount + "&tn=" + transactionNote;

            // Generate QR Code with larger dimensions
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(upiUrl, BarcodeFormat.QR_CODE, 300, 300); // Adjust size as needed

            // Convert to BufferedImage
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Create an ImageData object from byte array
            ImageData data = ImageDataFactory.create(imageBytes);

            // Create an image object from ImageData
            Image img = new Image(data);

            // Manually scale the image to fit the cell
            img.scaleToFit(100, 100); // Adjust these values as needed

            // Set cell properties
            this.setHeight(100f)
                .setPadding(0)
                .setMargin(0)
                .setBorder(new SolidBorder(ColorConstants.BLACK, 2f))
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
            this.add(img);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
