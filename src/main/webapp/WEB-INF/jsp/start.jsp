<%@ include file="/WEB-INF/jspf/include.jspf" %>

<html>
<body>

<div>
  <form:form method="post" commandName="trackCommand">
    <table border="1" cellspacing="0" cellpadding="4">
      <tr>
        <td align="right">
          Enter tracking id:
        </td>
        <td>
          <form:input path="trackingId"/>
        </td>
        <td>
          <form:errors path="trackingId" cssClass="error"/>
        </td>
      </tr>
    </table>
    <br>
    <input type="submit" value="Track!">
  </form:form>
</div>

</body>
</html>