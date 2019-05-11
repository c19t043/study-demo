# git
## 初始化git仓库
git init
## 克隆远程代码到本地
git clone 地址
git clone https://git.coding.net/wangdakeng/am-client.git
git clone https://git.coding.net/arboret/am-wap.git
## 添加远程仓库
git remote add origin 地址
git remote add origin https://git.coding.net/wangdakeng/am-client.git
git remote add origin https://git.coding.net/arboret/am-wap.git
## 拉去远程代码到本地
git fetch origin master(代码分支)
## 添加代码到本地仓库
git add 
## 查看当前代码状态
git status
## 提交代码到本地仓库
git commit -m '注释'
## 提交代码到远程仓库
git push origin master
## git pull错误
git config --system --unset credential.helper

## 查看分支
git branch 
## 创建分支
git branch v2
## 切换分支
git checkout v2
## 推送代码到分支
git push orgin v2（当前所在分支master，直接推送master中的代码到v2分支中）