# FTP Plug-in Project

> **네트워크를 통해 파일을 주고 받는 파일전송 서비스**

<br>

## 개발 환경

 [![license](https://img.shields.io/badge/java-1.8-yellow)](https://img.shields.io/badge/java-1.8-yellow) [![license](https://img.shields.io/badge/eclipse-4.10-green)](https://img.shields.io/badge/eclipse-4.10-green) [![license](https://img.shields.io/badge/windowbuilder-1.9.1-blue)](https://img.shields.io/badge/windowbuilder-1.9.1-blue)

<br>

## 협업 환경 

[![license](https://img.shields.io/badge/git-2.22-yellow.svg)](https://img.shields.io/badge/git-2.22-yellow) [![license](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-green.svg)](https://img.shields.io/badge/github-github.com%2F2hw%2FTeamPlug--in-green) [![license](https://img.shields.io/badge/sourceTree-3.13-blue.svg)](https://img.shields.io/badge/sourceTree-3.13-blue)

+ 깃허브를 통한 진행사항 및 이슈관리

<br>

## 개발 파트
1.  SFTP 연결
2.  local, 서버 디렉터리 구조 출력
3.  선택한 경로 표시 및 크기 표시
4.  디렉토리 생성 및 삭제
5.  업로드, 다운로드 기능

<br>

## 개발 일정 관리
> 개발 일정을 GITHUB의 Milestone과 Issue로 관리
일정을 가시적으로 표시하여 현재 진행도를 확인할 수 있다.


![image](https://user-images.githubusercontent.com/38846776/63754756-5d07a880-c8f0-11e9-9fad-e49740c5084c.png)
 + *Milestone   :  issue들의 그룹,  이정표로써 진행 상황을 표현*

![image](https://user-images.githubusercontent.com/38846776/63754982-bb348b80-c8f0-11e9-8d0a-4ac9ad07ad30.png)
  + *issue  :  프로젝트를 진행하면서 발생하는 모든 이슈 (버그 발생, 개발, 풀 리퀘스트 등등)*
  + *커밋 메세지를 통한 이슈 Close  처리*   ->  ``` (Keyword] [Issue Number] [commit emssage (생략가능)]```



  <br>

## 개발 구성


<br>

## 실행 화면

![image](https://user-images.githubusercontent.com/36910089/63744562-a77e2a80-c8da-11e9-9c4b-0ba902d2b575.png)

<br>


## 📂 Directory structure

```bash
                        
FileTransfer										#프로젝트
└─src
│   └─org
│       └─inbus
│           └─teamfiletransferclient
│               ├─core
│               │      FileTransferCore.java					    #컨트롤러
│               │
│               ├─exceptions
│               │      InvalidServerInformationException.java	    #예외 처리
│               │
│               ├─impl
│               │      IconImageUtil.java				   #아이콘을 표시하는 Util 클래스
│               │      SFTPUtil.java					   #SFTP 연결 클래스 
│               │      TableViewLabelProvider.java		  #Table에 표시되는 데이터 제어 클래스
│               │      TreeViewContentProvider.java		   #Tree에 보여질 노드를 리턴하는 클래스
│               │      TreeViewLabelProvider.java		  #Tree에 표시될 노드 제어 클래스
│               │
│               ├─model
│               │      ConnectionInfo.java						 #FTP 접속 정보 VO
│               │      Directory.java							#접속한 서버의 디렉터리 VO
│               │      FileTransfer.java						      #접속한 서버의 파일 정보 VO
│               │      TreeObject.java							#Tree VO
│               │      TreeParent.java							#하위 Tree VO
│               │
│               └─views
│                       FileTransferView.java					    #플러그인 메인 뷰
│	NewDirectoryDialog.java                           #새 디렉터리 생성 
├─lib
│      commons-lang-2.6.jar
│      jsch-0.1.55.jar							#SFTP 라이브러리
│
├─META-INF
│   MANIFEST.MF
│  .classpath
│  .gitignore
│  .project
│  build.properties
│  contexts.xml
│  plugin.xml
│  README.md
```
