import sys
sys.path.append('..')

from excelReader.excelReader import excel_reader

def excel_read(path = './entity_annotation.xlsx'):
    obj = excel_reader(path)
    ds = obj.xl_read()
    data = obj.xl_read_all(obj, ds)
    return data

def write_lines(data, path, num = 1000):
	print(data[1])
	with open(path, 'w') as f:
		for i,item in enumerate(data):
			if item[5] == '':
				continue
			if i == num:
				break
			if ',' in item[5]:
				item_spt = item[5].split(',')
				for j in item_spt:
					if j != '':
						f.write(item[1].strip() + '\t' + j.strip() + '\n')
			else:
				f.write(item[1].replace('\n', '') + '\t' + item[5].strip() + '\n')

if __name__ == '__main__':
	data = excel_read()
	write_lines(data, path = 'rules.txt')