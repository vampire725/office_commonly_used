# zsh配置

## 安装oh-my-zsh

1. 安装zsh
`sudo apt install zsh -y`
2. 修改shell为zsh
`sudo usermod -s /bin/zsh {yourusername}`
3. 安装oh-my-zsh
`sudo sh -c "$(wget https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh -O -)"`

安装或者`git clone https://github.com/robbyrussell/oh-my-zsh.git ~/.oh-my-zsh`

## (可选)安装Powerline

`pip install powerline-status`

## (可选)安装Powerline字体

1. `git clone https://github.com/powerline/fonts.git`

2. `cd fonts` `./install.sh`

## 安装语法高亮zsh-syntax-highlighting

`git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting`

## 安装自动建议zsh-autosuggestions

`git clone git://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions`

## 在.zshrc配置文件中启用插件

plugins=(
d
git
extract
zsh-autosuggestions
zsh-syntax-highlighting
ruby
)

配置环境变量支持256色
vim ~/.zshenv

export TERM="xterm-256color"

source .zshrc

[参考](https://github.com/robbyrussell/oh-my-zsh)
