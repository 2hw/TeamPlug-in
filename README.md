# FTP Plug-in Project

> **네트워크를 통해 파일을 주고 받는 파일전송 서비스**

## 개발 환경

 [![license](https://img.shields.io/badge/java-1.8-yellow)](https://img.shields.io/badge/java-1.8-yellow) [![license](https://img.shields.io/badge/eclipse-4.10-green)](https://img.shields.io/badge/eclipse-4.10-green)[![license](https://img.shields.io/badge/windowbuilder-1.9.1-blue)](https://img.shields.io/badge/windowbuilder-1.9.1-blue)

## 협업 환경 

[![license](https://img.shields.io/badge/git-2.22-yellow.svg)](https://img.shields.io/badge/git-2.22-yellow)[![license](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-green.svg)](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-green)[![license](https://img.shields.io/badge/sourceTree-3.13-blue.svg)](https://img.shields.io/badge/sourceTree-3.13-blue)

+ 깃허브를 통한 진행사항 및 이슈관리
  + *Milestone   :  issue들의 그룹,  이정표로써 진행 상황을 표현*
  + *issue  :  프로젝트를 진행하면서 발생하는 모든 이슈 (버그 발생, 개발, 풀 리퀘스트 등등)*
  + *커밋 메세지를 통한 이슈 Close 처리 (  [Keyword] [Issue Number] [commit emssage (생략가능)]*

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
│               │      FileTransferCore.java 											    	#컨트롤러
│               │
│               ├─exceptions
│               │      InvalidServerInformationException.java 		        		#예외 처리
│               │
│               ├─impl
│               │      IconImageUtil.java 										                  #아이콘을 표시하는 Util 클래스
│               │      SFTPUtil.java 											                       #SFTP 연결 클래스 
│               │      TableViewLabelProvider.java 							               #Table에 표시되는 데이터 제어 클래스
│               │      TreeViewContentProvider.java 				 		             #Tree에 보여질 노드를 리턴하는 클래스
│               │      TreeViewLabelProvider.java 							                #Tree에 표시될 노드 제어 클래스
│               │
│               ├─model
│               │      ConnectionInfoModel.java 	            					      #FTP 접속 정보 VO
│               │      DirectoryModel.java 											    	    #접속한 서버의 디렉터리 VO
│               │      FileTransferModel.java 										    		#접속한 서버의 파일 정보 VO
│               │      TreeObject.java 												                #Tree VO
│               │      TreeParent.java 												                #하위 Tree VO
│               │
│               └─views
│                       FileTransferView.java 										        		#플러그인 메인 뷰
|						NewDirectoryDialog.java
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
