package extras;

import javax.swing.*;
import javax.swing.text.*;

public class NumberOnlyTextField extends JTextField {
	private static final long serialVersionUID = 1L;

	public NumberOnlyTextField(String defaultValue) {
        super(defaultValue); // Set the default value
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new NumberFilter());
    }
	public NumberOnlyTextField() {
        super(); // Set the default value
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new NumberFilter());
    }

    private static class NumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isNumericOrEmpty(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isNumericOrEmpty(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isNumericOrEmpty(String str) {
            if (str.isEmpty()) {
                return true; // Allow empty string (null value)
            }
            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }
}
