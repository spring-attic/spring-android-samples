<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
    <head>
        <title>Upload a file please</title>
    </head>
    <body>
        <h1>Please upload a file</h1>
        <form method="post" action="/spring-android-showcase-server/postformdata" enctype="multipart/form-data">
            <p>Name: <input type="text" name="description"/></p>
            <p><input type="file" name="file"/></p>
            <p><input type="submit"/></p>
        </form>
    </body>
</html>