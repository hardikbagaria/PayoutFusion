# Payout Fusion

**Payout Fusion** is an all-in-one billing and payment solution designed to streamline the creation, management, and delivery of invoices for small to medium-sized businesses. This project automates invoice generation, facilitates payment collection through UPI integration, and provides an easy-to-use interface for managing parties and ledgers. 

The solution helps businesses reduce manual effort, enhances accuracy, and offers real-time invoice delivery via WhatsApp or email, ensuring a seamless customer experience.

---

## Table of Contents

1. [Overview](#overview)
2. [Features](#features)
3. [System Architecture](#system-architecture)
4. [Tech Stack](#tech-stack)
5. [Detailed Modules](#detailed-modules)
   - [Bill Management](#1-bill-management)
   - [Party Management](#2-party-management)
   - [Payment Integration](#3-payment-integration)
   - [Ledger Management](#4-ledger-management)
   - [Automated Delivery](#5-automated-delivery)
6. [Additional Features](#additional-features)
7. [Installation](#installation)
8. [Configuration](#configuration)
9. [Usage](#usage)
   - [Creating an Invoice](#creating-an-invoice)
   - [Sending Invoices via WhatsApp](#sending-invoices-via-whatsapp)
   - [Viewing Party Ledger](#viewing-party-ledger)
10. [Future Enhancements](#future-enhancements)
11. [Contributors](#contributors)

---

## Overview

Payout Fusion is designed to simplify business billing operations by automating the entire invoice lifecycle, from creation to payment collection. The system generates tax-compliant PDF invoices, complete with calculated values such as GST and transportation costs. Payments can be collected using auto-generated UPI QR codes embedded in the invoice. Businesses can track party-wise transaction details, including ledgers, and send invoices via WhatsApp or email with just a few clicks.

This project also enables dynamic management of parties, bills, and items, providing a structured, reliable, and automated system to reduce the chances of human error.

---

## Features

- **Automated PDF Invoices**: Generates detailed, tax-compliant invoices with itemized lists and calculated totals (including tax, transportation, and discounts).
  
- **Dynamic Bill Calculations**: Automatically calculates totals for each invoice, including:
  - Total Taxable Value
  - GST @ 18%
  - Transportation Cost
  - Grand Total
  
- **Party Management**: Add, update, and delete party details. Information like address, GST number, and contact details are dynamically fetched from the database and displayed in the UI.

- **UPI QR Code Integration**: Generates a UPI QR code for seamless payments. The QR code is auto-scaled and embedded in the invoice PDF for easy customer payment.

- **Ledger View**: View the detailed transaction history for a party over a custom date range, allowing businesses to track outstanding balances and payments.

- **Automated Invoice Delivery**: Send PDF invoices to customers via WhatsApp or email, automating the communication process and reducing manual effort.

- **Seamless User Experience**: User-friendly Java GUI with neatly organized fields, dropdowns, and tables for easy navigation and interaction.

- **Selenium Integration**: Automates the process of sending PDFs via WhatsApp by using Selenium to control WhatsApp Web.

- **Custom UI Elements**: Custom `JTextField` classes with default values, ensuring easy interaction with dynamically updated fields.

---

## System Architecture

Payout Fusion consists of a Java-based desktop application connected to a MySQL database. Invoices are generated as PDF files using the iText 7 library, and payments are facilitated via UPI QR codes. Data such as party details, bills, and items are stored in MySQL and retrieved via Java backend processes. The application also integrates with WhatsApp Web to automate invoice delivery using Selenium.

**Components**:
- **Frontend**: Java Swing for GUI.
- **Backend**: Java with integrated business logic for managing party and bill operations.
- **Database**: MySQL and Amazon AWS.
- **PDF Generation**: iText 7 Core.
- **WhatsApp Integration**: Selenium for automating PDF delivery.

---

## Tech Stack

- **Frontend**: Java Swing for building the GUI interface.
- **Backend**: Java for processing business logic and managing database interactions.
- **Database**: MySQL (Amazon AWS hosted).
- **PDF Generation**: iText 7 Core for creating professional-grade PDF invoices.
- **Automation**: Selenium for WhatsApp Web automation.
- **Payment Integration**: UPI QR code generation within PDF invoices.
- **Others**:
  - Java PrinterJob for handling PDF printing.
  - Custom `JTextField` classes for form input and dynamic interaction.

---

## Detailed Modules

### 1. Bill Management

This module handles the creation, updating, deletion, and viewing of bills. Bills are created with detailed itemized lists and dynamically calculated totals, including tax and transportation costs.

- **Table Structure**: The `BillsTable` consists of the following columns:
  - `BillNo`: Unique identifier for each bill.
  - `PartyName`: The name of the party the bill is associated with.
  - `Date`: The date the bill was created.
  - `VehicleDetails`: Any transportation-related details.
  - `Transportation`: Transportation costs.
  
- **Items Management**: Each bill contains multiple items, with columns for:
  - `ItemName`
  - `Quantity`
  - `Rate`
  - `Amount`

The amount for each item is calculated dynamically based on the entered quantity and rate.

### 2. Party Management

The Party Management module enables the user to add, update, delete, and view party details. Party information is stored in a MySQL database and includes fields like:
- `PartyName`
- `Address1`, `Address2`, `Address3`
- `GST Number`
- `Contact Person`
- `Phone Number`
- `Destination`

Validation is enforced for the GST number (alphanumeric, uppercase) and phone number (numeric).

### 3. Payment Integration

This module generates UPI QR codes dynamically within each PDF invoice. The QR code enables seamless payment collection when the customer scans the code using any UPI app.

The QR code is auto-scaled and inserted into a designated cell in the PDF, with dimensions that span multiple rows and columns to ensure visibility.

### 4. Ledger Management

The Ledger Management module allows businesses to view the transaction history for a selected party over a custom date range. The ledger displays:
- `Date`
- `Bill Number`
- `Amount`
- `Balance`

Ledger data is fetched in real-time from the database, and party details are dynamically populated from a dropdown list.

### 5. Automated Delivery

With integrated WhatsApp and email functionality, businesses can send invoices to customers with a single click. This module automates the sending process using Selenium for WhatsApp Web.

---

## Additional Features

### 1. Automatic Numeric to Word Conversion (in Bill Generation)

The **billGenGST** module includes functionality to convert the total invoice amount from numbers to words, ensuring the invoice is professional and compliant with business norms. For example, an invoice total of `₹1234.56` is automatically converted to `One Thousand Two Hundred Thirty-Four Rupees and Fifty-Six Paise` and displayed on the invoice.

This feature eliminates manual effort and ensures consistency across all invoices.

### 2. Number-only Text Fields for `Amount` and `Rate`

To avoid incorrect data entry, the `Amount` and `Rate` fields in the itemized list of the invoice are restricted to numeric input only. These fields use custom `JTextField` components that validate input in real time, ensuring only numerical values are entered.

If non-numeric values are attempted, the field blocks the input, providing a smooth and error-free user experience.

---

## Installation

### Prerequisites
- Java JDK 11 or higher
- MySQL Server (with access credentials)
- Amazon AWS for hosting the MySQL database
- Necessary libraries: iText 7 Core, Selenium

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/payout-fusion.git
   cd payout-fusion
   ```

2. Set up MySQL database:
   - Use the provided SQL scripts to create tables for parties, bills, and items.
   - Insert some sample data for testing purposes.

3. Update database credentials in the `Processes` class:
   - Modify the `Processes` class to include your MySQL connection details.

4. Build and run the project:
   ```bash
   javac PayoutFusion.java
   java PayoutFusion
   ```

---

## Configuration

- **Database Configuration**: Configure MySQL connection parameters in the `Processes` class.
- **Printer Configuration**: Ensure the default printer settings are configured for A4 paper and margins set to zero.
- **Selenium Setup**: Set up the Selenium WebDriver for WhatsApp Web automation.

---

## Usage

### Creating an Invoice

1. Navigate to the **Bills** section.
2. Select **Create Bill**.
3. Fill in the details (Party, Date, Items, etc.).
4. The application will automatically calculate the totals, including GST and Transportation costs.
5. Save the bill, and the invoice PDF will be generated.

### Sending Invoices via WhatsApp

1. After creating an invoice, navigate to the **Print** section.
2. Select **Send via WhatsApp**.
3. The application will open WhatsApp Web and send the invoice to the party's number.

### Viewing Party Ledger

1. Go to the **Ledger** section.
2. Select a party and specify the start and end date.
3. The ledger will display all transactions within the selected date range.

---

## Future Enhancements

- **Mobile App**: Build an Android app to manage invoices and parties remotely.
- **Cloud Storage**: Integration with AWS S3 for storing and managing invoices in the cloud.
- **Role-based Access**: Different user roles with varying levels of access to the system (Admin, Accountant, Viewer).
- **Advanced Reporting**: Provide performance metrics such as total revenue, outstanding payments, and business analytics.

---

## Contributors

- **Hardik H Bagaria** (Project Lead) - [GitHub Profile](https://github.com/hardikbagaria)
  
Feel free to reach out for any queries or suggestions regarding the project.

---

This detailed README should provide thorough insight into your Payout Fusion project! Let me know if you need any further modifications.

