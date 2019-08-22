# SFTP Plug-in Project

> 네트워크를 통해 파일을 주고 받는 파일전송 서비스

## 사용 예제

스크린 샷과 코드 예제를 통해 사용 방법을 자세히 설명합니다.

## 📂 Directory structure

```bash
                        
FileTransfer 													#프로젝트
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

## 개발 환경 설정

모든 개발 의존성 설치 방법과 자동 테스트 슈트 실행 방법을 운영체제 별로 작성합니다.

```sh
make install
npm test
```