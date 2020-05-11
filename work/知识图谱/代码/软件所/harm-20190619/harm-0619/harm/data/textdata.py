# 读取excel中的数据，存入txt文件。
import sys
sys.path.append('..')

from excelReader.excelReader import excel_reader

def excel_read(path = './-2019-05-29-reduct.xlsx'):
    obj = excel_reader(path)
    ds = obj.xl_read()
    data = obj.xl_read_all(obj, ds)
    return data

def write_lines(data, path, num = 0):
    with open(path, 'a') as f:
        for i,item in enumerate(data):
            print(i)
            f.write(item[0].replace('\n', '') + '\t' + str(item[1]) + '\n')
            if i == 1000:
                break   

# 读取无害数据
def harmless_excel_read(index = 2, path = './harmless20190614.xlsx'):
    obj = excel_reader(path)
    ds = obj.xl_read(index)
    data = obj.xl_read_all(obj, ds)
    return data

# 取无害数据的文本，没有类型
def harmless_write_lines(data, path, num):
    with open(path, 'a') as f:
        for i,item in enumerate(data):
            print(i)
            if i == num:
                break   
            f.write(str(item[1]).replace('\n', '') + '\n')
            
# 构建1000有害，9000无害的测试集。
def test_set():
    # 有害1000条
    harm_data = excel_read()
    write_lines(harm_data, './test_set.txt')

    # 无害9000条
    harmless_data = harmless_excel_read(0)
    harmless_write_lines(harmless_data, './test_set.txt', 5500)

    # harmless_data = harmless_excel_read(1)
    # harmless_write_lines(harmless_data, './test_set.txt', 1000)

    harmless_data = harmless_excel_read(2)
    harmless_write_lines(harmless_data, './test_set.txt', 3500)

if __name__ == '__main__':
    test_set()
