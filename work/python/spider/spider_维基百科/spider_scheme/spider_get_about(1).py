import requests
import csv


def save_csv(item):
    with open('result2.csv', 'a', newline="", encoding='utf-8') as datacsv:
        csvwriter = csv.writer(datacsv, dialect=("excel"))
        print(item)
        csvwriter.writerow(item)


def get_name_zh(item):
    save_csv([item.get('rdfs:label'), item.get('nameZh')])


if __name__ == '__main__':
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)'
                      ' AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36'
    }
    url = 'http://cnschema.org/data2/properties.json'

    response = requests.get(url, headers=headers, verify=False).json()
    # print(type(response))
    for item in response:
        # print(type(item))
        get_name_zh(item)
    # print(response[0])
