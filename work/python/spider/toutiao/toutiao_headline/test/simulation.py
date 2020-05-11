import time
from selenium import webdriver

from selenium.webdriver.chrome.options import Options

chrome_options = Options()
chrome_options.add_argument('--no-sandbox')
chrome_options.add_argument('--disable-dev-shm-usage')
chrome_options.add_argument('--headless')

driver = webdriver.Chrome('/opt/chromedriver',chrome_options=chrome_options)
# driver.get('http://www.google.com/xhtml')
# time.sleep(5)
# search_box = driver.find_element_by_name('q')
# search_box.send_keys('ChromeDriver')
# search_box.submit()
# time.sleep(5)
# driver.quit()

driver.get("http://www.baidu.com")
assert "百度" in driver.title
elem = driver.find_element_by_name("wd")
elem.send_keys("Eastmount")
driver.save_screenshot('baidu.png')
driver.close()
driver.quit()
