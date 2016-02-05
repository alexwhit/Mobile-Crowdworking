from selenium import webdriver
from selenium.webdriver.common.keys import Keys

driver = webdriver.Firefox()
driver.get("https://www.mturk.com/mturk/findhits?match=false")

sign_in = driver.find_element_by_id('lnkWorkerSignin')
sign_in.click()
email = driver.find_element_by_id('ap_email')
email.send_keys("whitakeralex94@yahoo.com")
password = driver.find_element_by_id('ap_password')
password.send_keys()

submit = driver.find_element_by_id('signInSubmit-input')
submit.click()

capsulelinks = driver.find_elements_by_partial_link_text("View a HIT")
capsulelinks[0].click()
# groupId = driver.find_element_by_name('groupId')

# print "GroupID", groupId.get_attribute('value')
# print "Signature"
signature = driver.find_element_by_name('signature')
print "Signature: ", signature.get_attribute('value')
# print groupId.get_attribute('value')
# # print len(capsulelinks)
# # for i in range(len(capsulelinks)):
# # 	try:
# # 		print(capsulelinks[i].click())
# # 	except:
# # 		pass
# # 	print i

# driver.close()

