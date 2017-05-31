<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
var type1={'apple' : '사과','banana':'바나나','melon':'멜론'};
document.write(type1.apple);

//2 배열 형식
var mnt = ['백두산','지리산','한라산','설악산'];
document.write('<li>' + mnt[0]);
document.write('<li>' + mnt[1]);

//3 key : value 형식안에 배열 사용
var mntF = {
		'apple' : '사과','banana':'바나나',
		'mnt':['백두산','지리산','한라산','설악산'],
		'melon':'멜론'
};

document.write('<li>'+mntF.apple);
document.write('<li>'+mntF.mnt[0]);
document.write('<li>'+mntF.melon);
document.write('<li>'+mntF.mnt);


//4 2차원 배열

var revier=[
	[1,2,3],
	[3,4,5],
	[6,7,8]
];

document.write('<hr/>');
document.write('<li>'+revier[0][0]);
document.write('<li>'+revier[2][2]);


//5 2차원배열(map)

var riverF = [
	{'apple':'사과','banana':'바나나'},
	{'han':'한강','du':'두만간'}
];

document.write('<li>'+riverF[0].banana);
document.write('<li>'+riverF[1].du);



// 1 key : value



// 2 배열형
// 3 배열 + map 형태(복합형)


</script>
</head>
<body>

</body>
</html>