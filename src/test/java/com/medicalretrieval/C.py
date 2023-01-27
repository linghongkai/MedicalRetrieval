#coding=utf-8
def extractTitle(strs):
    strs = strs.split('/')[-1]
    strs = strs.split('.')[0]
    strs = strs.split('_')[:-1]
    ans = ""
    for s in strs:
        ans += s
        if s != strs[-1]:
            ans += '_'

    return ans


extractTitle("./三维标测系统和单环状标测导管指示_省略_线性消融电学隔离肺静脉方法学评价_董建增.pdf")
