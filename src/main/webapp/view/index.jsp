<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
 <html>
    <head>
        <title>Contact data</title>
    </head>
    <body>
        <form method="post">
            <select name="language">
                <option value="en" ${langVariable=="en"?"selected":""}>English</option>
                <option value="de" ${langVariable=="de"?"selected":""}>Deutsch</option>
                <option value="ru" ${langVariable=="ru"?"selected":""}>Русский</option>
                <option value="ua" ${langVariable=="ua"?"selected":""}>Українська</option>
            </select>
            <input type="submit" name="setLanguage" value="Ok"/>
        </form>

         <form method="post">
             <table border="1">
                <caption><h2>${tableDescription}</h2></caption>
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
                         <td>
                            <c:if test="${not empty fn:trim(contact.name)}">
                                <c:out value="${contact.name}"/>
                            </c:if>
                         </td>
                         <td>
                            <c:if test="${not empty fn:trim(contact.lastName)}">
                                <c:out value="${contact.lastName}"/>
                            </c:if>
                         </td>
                         <td>
                            <c:if test="${not empty fn:trim(contact.nickname)}">
                                <c:out value="${contact.nickname}"/>
                            </c:if>
                         </td>
                         <td>
                            <c:if test="${not empty fn:trim(contact.phone)}">
                                <c:out value="${contact.phone}"/>
                            </c:if>
                         </td>
                         <td>
                            <c:if test="${not empty fn:trim(contact.id)}">
                                <c:out value="${contact.id}"/>
                            </c:if>
                         </td>
                         <td>
                            <input type="checkbox" name="selectedRows" value="${contact.rowId}"/>
                         </td>
                    </tr>
                 </c:forEach>
             </table>
             <br><br>
             <input type="submit" name="uniteRows" value="Unite rows"/>
             <input type="submit" name="deleteRows" value="Delete rows"/>
         </form>

         <h2>${inputDeclaration}</h2><br />

         <form method="post" action="">
            <table border="0">
                <tr>
                    <td>${nameColumn}</td>
                    <td><input type="text" name="name"></td>
                </tr>
                <tr>
                    <td>${lastNameColumn}</td>
                    <td><input type="text" name="lastname"></td>
                </tr>
                <tr>
                    <td>${nicknameColumn}</td>
                    <td><input type="text" name="nickname"></td>
                </tr>
                <tr>
                    <td>${phoneColumn}</td>
                    <td><input type="text" name="phone"></td>
                </tr>
                <tr>
                    <td>${idColumn}</td>
                    <td><input type="text" name="id"></td>
                </tr>
            </table>
             <input type="submit" value="Ok" name="addValue"><br>
        </form>
    </body>
</html>