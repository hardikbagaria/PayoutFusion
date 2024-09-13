from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
import os
import time
import sys

def send_whatsapp_message(file_path):
    chrome_options = Options()
    user_data_dir = os.path.expanduser('~') + "\\Documents\\WhatsAppSession"
    chrome_options.add_argument(f"user-data-dir={user_data_dir}")

    service = Service()  # Assuming ChromeDriver is in the system PATH

    driver = webdriver.Chrome(service=service, options=chrome_options)
    driver.get('https://web.whatsapp.com/send?phone=+919022180909')  # Replace with the recipient's phone number

    wait = WebDriverWait(driver, 120)

    try:
        # Wait for the chat to load and then locate the attachment button
        attachment_icon = wait.until(EC.presence_of_element_located((By.XPATH, '//div[@title="Attach"]')))
        attachment_icon.click()

        # Click on the 'Document' option
        document_button = wait.until(EC.presence_of_element_located((By.XPATH, '//input[@accept="*"]')))
        document_button.send_keys(os.path.abspath(file_path))

        # Add a delay and then locate the send button
        send_button = wait.until(EC.element_to_be_clickable((By.XPATH, '//span[@data-icon="send"]')))
        send_button.click()

        # Wait for the file to be sent (you can adjust the sleep time as needed)
        time.sleep(10)

    except Exception as e:
        print(f"An error occurred: {e}")

    finally:
        driver.quit()
# Check if the file path is provided through command-line argument
if len(sys.argv) > 1:
    file_path = sys.argv[1]
    if os.path.exists(file_path):
        send_whatsapp_message(file_path)
    else:
        print("File does not exist.")
else:
    print("File path not provided.")
