# coding=UTF-8
def extractAuthor(strs):
    authors = []
    strs = strs.split("摘要")[0]
    return strs