# SFTP Plug-in Project

> **네트워크를 통해 파일을 주고 받는 파일전송 서비스**

## 협업 환경 

[![license](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-yellow.svg)](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-yellow) [![license](https://img.shields.io/badge/git-2.22-green.svg)](https://img.shields.io/badge/git-2.22-green) [![license](https://img.shields.io/badge/sourceTree-3.13-blue.svg)](https://img.shields.io/badge/sourceTree-3.13-blue)

+ 깃허브를 통한 진행사항 관리
  + *Milestone   :  issue들의 그룹,  이정표로써 진행 상황을 표현*
  + *issue  :  프로젝트를 진행하면서 발생하는 모든 이슈 (버그 발생, 개발, 풀 리퀘스트 등등)*
  + *커밋 메세지를 통한 이슈 Close (  [Keyword] [Issue Number] [commit emssage (생략가능)]*

## 실행화면

![image](https://user-images.githubusercontent.com/36910089/63557201-bb482a80-c582-11e9-9df9-2353cc5c6509.png)

## 📂 Directory structure

```bash
                        
FileTransfer 												#프로젝트
└─src
│   └─org
│       └─inbus
│           └─teamfiletransferclient
│               ├─core
│               │      FileTransfer.java
│               │
│               ├─exceptions
│               │      InvalidServerInformationException.java
│               │
│               ├─impl
│               │      IconImageUtil.java
│               │      SFTPUtil.java
│               │      TableViewLabelProvider.java
│               │      TreeViewContentProvider.java
│               │      TreeViewLabelProvider.java
│               │
│               ├─model
│               │      ConnectionInfoModel.java
│               │      DirectoryModel.java
│               │      FileTransferModel.java
│               │      TreeObject.java
│               │      TreeParent.java
│               │
│               └─views
│                       FileTransferView.java
├─lib
│      commons-lang-2.6.jar
│      jsch-0.1.55.jar
│
├─META-INF
│      MANIFEST.MF
│  .classpath
│  .gitignore
│  .project
│  build.properties
│  contexts.xml
│  plugin.xml
│  README.md
```
