<!DOCTYPE html>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.sql.Timestamp" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload Files</title>
</head>
<body>
    <h1>Select Files</h1>
    <form action="/uploadFile" method="POST" enctype="multipart/form-data">
        <input type="file" name="files" multiple />
        <input type="submit" value="Upload" />
    </form>

    <a href="/generatePdf">Generate User's PDF</a>
    <p>========================================================</p>
    <c:forEach var="singleDay" items="${allDates}">
        <p><c:out value="${Timestamp.valueOf(singleDay.atStartOfDay())}"/></p>
    </c:forEach>

</body>
</html>