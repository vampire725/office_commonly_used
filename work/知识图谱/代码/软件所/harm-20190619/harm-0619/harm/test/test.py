import sys
import re
import math
import random
sys.path.append('..')

from harmful.judge_harmfulstring import read_harmfultype_rules, rule_model

def judge(test_file, harm_num = 200):
	rules = rule_model()

	with open(test_file, 'r') as test_file:

		harm_index = 0
		harm_index_true = 0
		
		for i,line in enumerate(test_file):
			print(i)
			if not len(line):
				continue

			se = r'[。|，|《|》|？|、| …|“|：|”|\n|；|〉|〈|！|—|，|>]'
			line = re.sub(se, '', line)
			line = line.replace('/', '').replace('\\', '').replace('"', '')

			line_split = line.split('\t')
			text = line_split[0].strip()

			if i < harm_num:
				harm_type = line_split[1].strip()

			# 有害识别
			text_res, harm_type_res, keyword, rule = read_harmfultype_rules(rules, text)

			if harm_type_res != 'N':
				harm_index += 1
				print('--------')
				with open('./test_result.txt', 'a') as f:
					f.write(str(i + 1) + '	'+ rule + '	' + str(text_res) + '\t' + str(keyword) + '\n')

				if i < harm_num:
					harm_index_true += 1


	precision = harm_index_true/harm_index
	recall = harm_index_true/harm_num

	return precision, recall

# 按25%, 50%, 75%, 100%比例的规则，测试规则数量改变后，识别方法的效果变化情况。
def scale_test(scale, test_file, harm_num, out_file):
	rules = rule_model()

	scale_rules = random.sample(rules, math.ceil(len(rules) * scale))

	with open(test_file, 'r') as test_file:

		harm_index = 0
		harm_index_true = 0
		
		for i,line in enumerate(test_file):
			print(i)
			if not len(line):
				continue

			se = r'[。|，|《|》|？|、| …|“|：|”|\n|；|〉|〈|！|—|，|>]'
			line = re.sub(se, '', line)
			line = line.replace('/', '').replace('\\', '').replace('"', '')

			line_split = line.split('\t')
			text = line_split[0].strip()

			if i < harm_num:
				harm_type = line_split[1].strip()

			# 有害识别
			text_res, harm_type_res, keyword, rule = read_harmfultype_rules(scale_rules, text)

			if harm_type_res != 'N':
				harm_index += 1
				print('--------')
				with open(out_file, 'a') as f:
					f.write(str(i + 1) + '	'+ rule + '	' + str(text_res) + '\t' + str(keyword) + '\n')

				if i < harm_num:
					harm_index_true += 1


	precision = harm_index_true/harm_index
	recall = harm_index_true/harm_num
	
	return precision, recall


# 按1千有害，9千无害数据进行测试。计算准确率和召回率。

# 规则的漏报率。漏报误报比较高的规则。


if __name__ == '__main__':
	# precision, recall = judge('../data/test_set(1000).txt')
	# print(precision, recall)
	# with open('./test_result.txt', 'a') as f:
	# 	f.write(str(precision) + '\t' + str(recall))

	# 1k数据
	scales = [0.50, 0.75, 1]
	test_file = '../data/test_set(1000).txt'

	for i in scales:
		file = './1k' + str(i) + '.txt'
		precision, recall = scale_test(i, test_file, 200, file)
		with open(file, 'a') as f:
			f.write(str(precision) + '\t' + str(recall))

	# 1w条数据
	# scales = [0.50, 0.75, 1]
	# test_file = '../data/test_set.txt'

	# for i in scales:
	# 	file = './1w' + str(i) + '.txt'
	# 	precision, recall = scale_test(i, test_file, 1000, file)
	# 	with open(file, 'a') as f:
	# 		f.write(str(precision) + '\t' + str(recall))



