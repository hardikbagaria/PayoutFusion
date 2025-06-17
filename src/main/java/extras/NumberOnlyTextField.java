// File: extras/NumberOnlyTextField.java
package extras;

import javax.swing.*;
import javax.swing.text.*;

public class NumberOnlyTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    public NumberOnlyTextField(String defaultValue) {
        super(defaultValue);
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new NumberFilter());
    }

    public NumberOnlyTextField() {
        super();
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new NumberFilter());
    }

    private static class NumberFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            newText.insert(offset, string);
            if (isValidNumberInput(newText.toString())) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            StringBuilder newText = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()));
            newText.replace(offset, offset + length, text);
            if (isValidNumberInput(newText.toString())) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
        }

        private boolean isValidNumberInput(String str) {
            if (str.isEmpty()) {
                return true;
            }
            // Allow only digits and at most one dot
            return str.matches("\\d*\\.?\\d*");
        }
    }
}
