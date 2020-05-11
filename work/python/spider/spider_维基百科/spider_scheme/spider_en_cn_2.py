import requests
import csv


def save_csv(item):
    print(type(item), item)
    with open('result.csv', 'a', newline="", encoding='utf-8') as data_csv:
        csv_writer = csv.writer(data_csv, dialect=('excel'))
        csv_writer.writerow(item)


def get_name_zh(item):
    if isinstance(item, dict) and item.get('children'):
        save_csv([item.get('name'), item.get('nameZh')])
        # print([item.get('name'), item.get('nameZh')])
        item = item.get('children')
        for l_item in item:
            get_name_zh(l_item)
    else:
        save_csv([item.get('name'), item.get('nameZh')])


if __name__ == '__main__':
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'
                      ' AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36'
    }
    url = 'http://cnschema.org/data2/classes.json'

    response = requests.get(url, headers=headers, verify=False).json()
    get_name_zh(response)
