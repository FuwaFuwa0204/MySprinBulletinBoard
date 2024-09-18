# FUWAFUWA FANMADE T1 CALENDAR
![t1logo](https://github.com/user-attachments/assets/4219349b-de4e-4533-82f3-2c85ad29674c)
<p align="center">
🚩 FUWAFUWA FANMADE T1 CALENDAR는 이스포츠 리그오브레전드 프로게임선수단 T1의 경기 일정을 자유롭게 추가 및 조회가 가능하고 의견을 나눌 수 있는 사이트입니다. 본 사이트는 티원 공식의 허락을 받아 만든 팬메이드 비영리적 사이트로 설립/운영상 티원 구단과는 아무런 관계가 없습니다. 
</p>


## 프로젝트 개요
    1. 프로젝트 이름 : FUWAFUWA FANMADE T1 CALENDAR
    2. 배포 주소 : https://fuwafuwacal.site (현재 서버를 종료하였습니다.)
    3. 프로젝트 개발 기간 : 2024.03.26 ~ 2024.07.29    


## 기술 스택
<img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white">  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=Spring%20Boot&logoColor=black"/>
<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white"/>
<img src="https://img.shields.io/badge/Bootstrapap-7952B3?style=flat-square&logo=bootstrap&logoColor=white"/>
<img src="https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white"/>
<img src="https://img.shields.io/badge/GitHub-181717?style=flat-square&logo=GitHub&logoColor=white"/>
<img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&logo=jQuery&logoColor=white"/>
<img src="https://img.shields.io/badge/JSON-000000?style=flat-square&logo=json&logoColor=white"/>
<img src="https://img.shields.io/badge/Ubuntu-E95420?style=flat-square&logo=Ubuntu&logoColor=white"/>
<img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=PostgreSQL&logoColor=white"/>
<img src="https://img.shields.io/badge/Amazon%20S3-569A31?style=flat-square&logo=Amazon%20S3&logoColor=white">
<img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=white">
<img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=white">
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=flat-square&logo=JavaScript&logoColor=white">
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=Thymeleaf&logoColor=white">



## 페이지별 기능


  ### 메인페이지 - <일정보기>


![fuwafuwacal site_question_list_calendar](https://github.com/user-attachments/assets/60dc1461-60af-4793-bfe6-e7913a7c1212)


![fuwafuwacal site_question_list_calendar (1)](https://github.com/user-attachments/assets/5f293198-cc1b-46d1-b18b-a968373aa5e4)


    일정은 바로바로 확인하는 것이 좋다고 생각했기때문에 초기 화면을 일정 확인 페이지로 설정하였습니다. 달력은 full calendar 라이브러리를 이용하였습니다. 로그인을 한 사용자라면 일정추가 버튼을 눌러 일정을 추가할 수 있습니다. 만약 로그인을 하지 않았다면 로그인 페이지로 자동으로 넘어가게됩니다. 일정 추가 모달창에서 경기 일정을 체크하면 일정이 빨간색으로 표시되고 경기 외 일정을 체크하면 일정이 연두색으로 표시되게 만들었습니다.



  ### <자유게시판>


![image](https://github.com/user-attachments/assets/26790a7f-1141-45e6-a28d-9c4b88028473)


    자유롭게 게시글을 작성할 수 있는 자유게시판 기능입니다. 질문 게시글들은 페이징 처리하였습니다. 글쓰기 버튼을 누르면 글 등록 페이지로 넘어갑니다. 


  ### <글등록>


![fuwafuwacal site_question_create_qna](https://github.com/user-attachments/assets/0d92ac4c-b35f-4f44-b875-f0ca776b2aaa)


    글 등록을 할때 여러 개의 이미지 파일도 같이 등록할 수 있습니다. 이미지 파일의 저장은 aws s3를 사용하였습니다.



   ### <글 디테일>


![image](https://github.com/user-attachments/assets/6257233f-2277-4556-b438-5c1d4f5e7a3e)


    질문에 추천, 삭제, 수정 기능이 있으며 글 사용자와 현재 로그인 사용자가 같아야 삭제 및 수정이 가능합니다. 그리고 질문에 댓글을 달 수 있으며 계층형 댓글로 댓글에 대댓글을 계속 달 수 있습니다. 댓글에도 수정, 삭제, 답글 기능이 있습니다.


  ### <로그인>


![fuwafuwacal site_user_login](https://github.com/user-attachments/assets/230e1715-012b-4fbd-83b2-713740b7291f)


    로그인 화면입니다. 구글 소셜 로그인을 넣어 구글 계정을 가진 사람이라면 간편하게 구글 계정으로 로그인 할 수 있도록 하였습니다.


    1. 아이디 찾기

    
![fuwafuwacal site_user_findid](https://github.com/user-attachments/assets/ad3e7705-b982-4a70-9f93-dd4c533dcff0)


    자신의 이메일로 아이디를 찾을 수 있는 기능입니다.


    2. 임시 비밀번호 발급하기


![fuwafuwacal site_user_findpassword](https://github.com/user-attachments/assets/5341b7a6-4aae-43a2-b069-b95f7d127018)


    JavaMailSender를 이용하여 이메일로 임시비밀번호를 발급할 수 있는 기능을 만들었습니다.


![image](https://github.com/user-attachments/assets/e9583114-5350-4416-bcbe-51576e883d38)


    메일을 수신하면 위와 같은 화면으로 임시 비밀번호를 발급할 수 있으며 마이페이지에서 비밀번호를 변경할 수 있습니다.


  ### <회원가입>


![fuwafuwacal site_user_signup](https://github.com/user-attachments/assets/0e39cb8f-6a72-4592-8623-c1a70a2fa269)



  ### <마이페이지>


![image](https://github.com/user-attachments/assets/c918cc8e-f9b3-4aaf-b451-1a361e98d0f9)


![image](https://github.com/user-attachments/assets/570540fc-c355-459a-85c2-1ba6894fabe4)


    내가 쓴 질문, 댓글을 입력하면 자신이 쓴 질문과 댓글을 확인할 수 있는 페이지로 넘어갑니다.





