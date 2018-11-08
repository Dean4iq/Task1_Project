<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
 <html>
    <head>
        <title>Contact data</title>
    </head>
    <body>
        <form method="get">

        </form>

        <h1>${greeting}</h1><br />

         <table border="1">
            <caption>${tableDescription}</caption>
            <tr>
                <th>
                    ${nameColumn}
                    <form method="post">
                        <input type="submit" name="sortNameAsc" value="↑"/>
                        <input type="submit" name="sortNameDesc" value="↓"/>
                    </form>
                </th>
                <th>
                    ${lastNameColumn}
                    <form method="post">
                        <input type="submit" name="sortLastNameAsc" value="↑"/>
                        <input type="submit" name="sortLastNameDesc" value="↓"/>
                    </form>
                </th>
                <th>${nicknameColumn}</th>
                <th>${phoneColumn}</th>
                <th>${idColumn}</th>
            </tr>
             <c:forEach var="contact" items="${requestScope.contacts}">
                <tr>
                     <td><c:out value="${contact.name}"/></td>
                     <td><c:out value="${contact.lastName}"/></td>
                     <td><c:out value="${contact.nickname}"/></td>
                     <td><c:out value="${contact.phone}"/></td>
                     <td><c:out value="${contact.id}"/></td>
                </tr>
             </c:forEach>
         </table>

         <h2>${inputDeclaration}</h2><br />

         <form method="post" action="">
             ${nameColumn}<label><input type="text" name="name"></label><br>
             ${lastNameColumn}<label><input type="text" name="lastname"></label><br>
             ${nicknameColumn}<label><input type="text" name="nickname"></label><br>
             ${phoneColumn}<label><input type="text" name="phone"></label><br>
             ${idColumn}<label><input type="text" name="id"></label><br>
             <input type="submit" value="Ok" name="addValue"><br>
        </form>
    </body>
</html>