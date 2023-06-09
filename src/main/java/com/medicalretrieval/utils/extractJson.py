# coding=utf-8

import jsonpath_rw_ext as jp
import json
import os
import zipfile
import sys
import io


def extract(var):
    with zipfile.ZipFile('E:/workspace/MedicalRetrieval/' + var[2:], 'r') as zip_ref:
        zip_ref.extractall('E:/workspace/MedicalRetrieval/temp/destination_folder')
    load_dict = {}
    with io.open("E:/workspace/MedicalRetrieval/temp/destination_folder/structuredData.json") as load_f:
        load_dict = json.load(load_f)
    # os.remove("E:/workspace/MedicalRetrieval/temp/destination_folder/structuredData.json")
    # os.remove('E:/workspace/MedicalRetrieval/' + var[2:])
    data = load_dict

    # 提取出关键信息
    json_list_title = jp.match('$.elements[?(@.Path ~ ".*Title.*")].Text', data)[0]
    json_list_authors = jp.match('$.elements[?(@.Path ~ ".*H1.*")].Text', data)[0]
    json_list_keyWords = jp.match('$.elements[?(@.Text ~ ".*关键词.*")].Text', data)[0]
    json_list_abstract = jp.match('$.elements[?(@.Text ~ ".*摘要.*")].Text', data)[0]
    # json_list_abstract.append(jp.match('$.elements[?(@.Text ~ ".*Abstract.*")].Text', data)[0])
    return json_list_title, json_list_authors, json_list_abstract, json_list_keyWords, data


if __name__ == '__main__':
    a = []
    for i in range(1, len(sys.argv)):
        a.append((str(sys.argv[i])))
    # json_list = extract(a[0])
    json_list = extract('./temp/123.zip')
    json_list_str = json_list[1]
    authors = json_list_str.split('\u3000')
    # authors = '关振鹏\u3000吕厚山\u3000陈彦章\u3000宋奕宁\u3000秦秀龙\u3000姜军'.split('\u3000')
    titles = json_list[0].split('\u3000')
    abstracts = json_list[2]
    abstracts = abstracts.replace(" ", "")
    abstracts = abstracts.replace('\u3000', '')
    keywords = json_list[3].split('\u3000')
    for title in titles:
        print(title)
    print('------')
    for author in authors:
        print(author)
    print('------')
    abc = '12 4'

    # for abstract in abstracts:
    #     print(abstract)
    print(abstracts)
    print('------')
    for k in keywords:
        print(k)
    print('------')
    data = json_list[4]
    # 总页码

    n = int(jp.match('$.extended_metadata.page_count', data)[0])
    print(n)
    for i in range(0, n):
        paragraph_list = jp.match('$.elements[?(@.Page==' + str(i) + ')].Text', data)
        for paragraph in paragraph_list:
            paragraph = str(paragraph).replace(' ', '')
            paragraph = paragraph.replace('\u3000', '')
            print(paragraph)
        print('------')
