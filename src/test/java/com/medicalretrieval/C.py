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
    print(ans)
    return ans

print(13)
extractTitle("./影响人工关节置换术后下肢深静脉血栓形成的临床风险因素分析_关振鹏.pdf")
