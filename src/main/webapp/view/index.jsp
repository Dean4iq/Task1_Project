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
                <option value="de" ${langVariable=="de"?"selected":""}>Deutsch</option>
                <option value="en" ${langVariable=="en"?"selected":""}>English</option>
                <option value="ru" ${langVariable=="ru"?"selected":""}>Русский</option>
                <option value="ua" ${langVariable=="ua"?"selected":""}>Українська</option>
            </select>
            <input type="submit" name="setLanguage" value="Ok"/>
        </form>

        <div style="position:relative;justify-content:space-between;display:flex;">
            <div align="center" style="width:100%;padding-right:10px;">
                <form method="post">
                    <table border="1">
                        <caption><h2>${tableDescription}</h2></caption>
                        <tr align="center">
                            <th width="20%">
                                ${nameColumn}
                                <form method="post">
                                    <input type="submit" name="sortNameAsc" value="↑"/>
                                    <input type="submit" name="sortNameDesc" value="↓"/>
                                </form>
                            </th>
                            <th width="20%">
                                ${lastNameColumn}
                                <form method="post">
                                    <input type="submit" name="sortLastNameAsc" value="↑"/>
                                    <input type="submit" name="sortLastNameDesc" value="↓"/>
                                </form>
                            </th>
                            <th width="20%">${nicknameColumn}</th>
                            <th width="20%">${phoneColumn}</th>
                            <th width="20%">${idColumn}</th>
                        </tr>
                         <c:forEach var="contact" items="${requestScope.contacts}">
                            <tr align="center">
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
                     <input type="submit" name="uniteRows" value="${buttonUnite}"/>
                     <input type="submit" name="deleteRows" value="${buttonDelete}"/>
                 </form>

                 <p style="border-bottom:1px solid;"></p>
                 <h2>${inputDeclaration}</h2><br />

         <form method="post" action="">
            <table border="0">
                <tr>
                    <td>${nameColumn}</td>
                    <td><input type="text" name="name"></td>
                    <td>${regexStrings.regexName}</td>
                </tr>
                <tr>
                    <td>${lastNameColumn}</td>
                    <td><input type="text" name="lastname"></td>
                    <td>${regexStrings.regexLastName}</td>
                </tr>
                <tr>
                    <td>${nicknameColumn}</td>
                    <td><input type="text" name="nickname"></td>
                    <td>${regexStrings.regexNickname}</td>
                </tr>
                <tr>
                    <td>${phoneColumn}</td>
                    <td><input type="text" name="phone"></td>
                    <td>${regexStrings.regexPhone}</td>
                </tr>
                <tr>
                    <td>${idColumn}</td>
                    <td><input type="text" name="id"></td>
                    <td>${regexStrings.regexId}</td>
                </tr>
            </table>
             <input type="submit" value="${buttonAdd}" name="addValue"><br>
        </form>
        </div>

        <div style="width:300px; float:right; text-align:left;background:lightgrey;">
                        <h1 align="center">FAQ</h1>
                        ${FAQContent}
                    </div>
        </div>
    </body>
</html>