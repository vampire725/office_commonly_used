#!/usr/bin/env python
# -*- coding: utf-8 -*-
import xlrd

class excel_reader:
	__path = '' # 读取文件的路径

	def __init__(self, path):
		self.__path = path

	# 设置当前对象使用的文件路径
	def set_path(self, path):
		self.__path = path

	# 返回当前对象使用的文件路径
	def return_path(self):
		return self.__path

	def return_sheet_num(self):
		return self.__sheet_num

	# 获取Excel表单，即一个sheet。sheet使用sheetIndex指定（默认获取第一个）
	def xl_read(self, sheetIndex = 0):
		workbook = xlrd.open_workbook(self.return_path())
		self.__sheet_num = len(workbook.sheets())
		dataSheet = workbook.sheet_by_index(sheetIndex)
		return dataSheet

	# 获取sheet的标签 
	def get_lable(self, ds):
		label = []
		colNum = ds.ncols
		for i in range(colNum):
			label.append(ds.cell_value(0, i))
		return label

	# 获取一行Excel数据,返回值为字典格式。默认读取第一行。
	def xl_line_header(self, ds, line_index = 1):
		line = dict()
		col_num = ds.ncols
		label_list = self.get_lable(ds)
		for i in range(col_num):
			line[label_list[i]] = ds.cell_value(line_index, i)
		return line

	# 获取一行Excel数据，返回数组格式，无表头
	def xl_line(self, ds, line_index = 0):
		line = []
		col_num = ds.ncols
		for i in range(col_num):
			line.append(ds.cell_value(line_index, i))

		return line

	# 获取datasheet中全部数据
	def xl_read_all(self, obj, ds):
		data_all = []
		row_num = ds.nrows

		for i in range(row_num):
			data_all.append(obj.xl_line(ds, i))

		return data_all



if __name__ == '__main__':
	path = '../../data/3_15data(weibo)/ceshi.xls'
	print(path)