<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actFol" value="${ForwardConst.ACT_FOL.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
    <h2>フォロー 一覧</h2>
    <table id="follow_list">
        <tbody>
            <tr>
                <th class="followee_code">社員番号</th>
                <th class="followee_name">氏名</th>
                <th class="followee_action">レポート一覧</th>
            </tr>
            <c:forEach var="followee" items="${follows}" varStatus="status">

                <tr class="raw${status.count % 2}">
                    <td class="followee_code"><c:out value="${followee.employee.code}" /></td>
                    <td class="followee_name"><c:out value="${followee.employee.name}" /></td>
                    <td class="follow_action"><a href="<c:url value='?action=${actRep}&command=${commIdx}' />">レポート一覧</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="pagination">
        (全 ${followee_count} 件)<br />
        <c:forEach var="i" begin="1" end="${((followee_count - 1) / maxRow) + 1}" step="1">
            <c:choose>
                <c:when test="${i == page}">
                    <c:out value="${i}" />&nbsp;
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='?action=${actFol}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    </c:param>
</c:import>