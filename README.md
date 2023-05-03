# zhihuribao
知乎日报
# 简单的介绍
  app，粗略(真)的仿造了知乎日报，实现了首页的轮播图，实现了首页的消息列表，同时可以点入查看内容，下方可以点入查看评论界面。
# 详细的介绍
## 首页
### 顶部标题栏
  采用toolbar，同时通过读取了本地时间，左上角可以根据时间改变
### 轮播图
  轮播图采用vp2+fragment实现，同时内容信息也可以显示出来，可以每五秒自动更换一次，右下角点也跟着改变，同时注意右下角有我精心制作的五个点，真·轮播指示器(不是)，
### 内容
   内容采用recycleView制作。
## 内容界面
  就是采用vp2+fragment，通过webView显示网页。然后下面有评论点赞
## 评论区
  采用recyclerView

# 技术方面
图片：所有的图片采用Glide

网络请求：所有的网络请求均为retrofit+rxjava+viewModel+LiveData

适配器：recyclerView均为差分刷新

刷新：采用SwipeRefreshLayout

轮播效果：采用hander的postValue设置时间来达到轮播效果
