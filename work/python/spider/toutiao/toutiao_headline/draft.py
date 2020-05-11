recoderDir = r"crawls"
checkFile = "isRunning.txt"

start_time = datetime.datetime.now()
print("startTime = {}".format(start_time))

i = 0
minute = 0
while True:
    isRunning = os.path.isfile(checkFile)
    if not isRunning:
        is_exit = os.path.isdir(recoderDir)  #
        print("mySpider not running, ready to start. is_exit:{}".format(is_exit))
        if is_exit:
            shutil.rmtree(recoderDir)  # 删除续爬目录crawls及目录下所有文件
            print("At time:{}, delete res".format(datetime.datetime.now()))
        else:
            print("At time:{}, Dir:{} is not exit.".format(datetime.datetime.now(), recoderDir))
        time.sleep(20)
        crawl_time = datetime.datetime.now()
        wait_time = crawl_time - start_time
        print("At time:{}, start crawl: toutiao !!!, waitTime:{}".format(crawl_time, wait_time))
        execute('scrapy crawl toutiao -s JOBDIR=crawls/storeMyRequest'.split())
        break
    else:
        print("At time:{}, mySpider is running, sleep to wait.".format(datetime.datetime.now()))
    i += 1
    time.sleep(60)
    minute += 10
    if minute >= 144550:
        break
