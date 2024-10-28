<%@page import="model.bean.Book"%>
<%@page import="java.util.ArrayList"%>
<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book List</title>
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
</head>

<body>

	<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("login.jsp?error= Please log in");
		return;
	}

	// paged book list
	@SuppressWarnings("unchecked")
	ArrayList<Book> pagedBookList = (ArrayList<Book>) request.getAttribute("pagedBookList");

	int currentPage = (Integer) request.getAttribute("currentPage");
	int totalPages = (Integer) request.getAttribute("totalPages");
	int startPage = (Integer) request.getAttribute("startPage");
	int endPage = (Integer) request.getAttribute("endPage");

	String searchText = (String) session.getAttribute("searchText");
	String status = (String) session.getAttribute("status");

	String routePrefix = searchText == null ? "ShowBookList" : "SearchBooks";
	%>

	<div class="container mb-5">
		<div class="row justify-content-center">
			<div class="col-md-12">
				<h2 class="text-center my-4">Book Inventory</h2>

				<!-- Search Bar -->
				<nav class="navbar bg-body-tertiary mt-5">
					<div class="container-fluid justify-content-center">
						<form
							action="SearchBooksServlet"
							method="post"
							class="d-flex w-50 me-3"
							role="search">

							<!-- Input: search Text -->
							<input
								class="form-control me-3"
								type="search"
								name="searchText"
								value="<%=searchText != null ? searchText : ""%>"
								placeholder="Search"
								aria-label="Search">

							<!-- Select box: status -->
							<select
								class="form-select me-3 w-25"
								aria-label="Default select example"
								name="status">
								<option
									<%=(status == null || "all".equals(status)) ? "selected" : ""%>
									value="all">Status</option>
								<option
									<%="available".equals(status) ? "selected" : ""%>
									value="available">Available</option>
								<option
									<%="borrowed".equals(status) ? "selected" : ""%>
									value="borrowed">Borrowed</option>
								<option
									<%="reserved".equals(status) ? "selected" : ""%>
									value="reserved">Reserved</option>
								<option
									<%="overdue".equals(status) ? "selected" : ""%>
									value="overdue">Overdue</option>
								<option
									<%="lost".equals(status) ? "selected" : ""%>
									value="lost">Lost</option>
								<option
									<%="damaged".equals(status) ? "selected" : ""%>
									value="damaged">Damaged</option>
								<option
									<%="in repair".equals(status) ? "selected" : ""%>
									value="in repair">In repair</option>
							</select>

							<button
								class="btn btn-outline-success"
								type="submit">Search</button>
						</form>


						<!-- Clear Search Button -->
						<a
							class="btn btn-outline-secondary me-3"
							href="ShowBookListServlet"
							role="button">Clear Search</a>

					</div>
				</nav>

				<!-- Book List Table -->
				<table class="table table-striped mt-4 mb-5">
					<thead>
						<tr>
							<th scope="col">No.</th>
							<th scope="col">Title</th>
							<th scope="col">Author Name</th>
							<th scope="col">Genre</th>
							<th scope="col">Language</th>
							<th scope="col">Status</th>
							<th scope="col">Rating</th>
						</tr>
					</thead>

					<tbody>

						<%
						if (pagedBookList.size() > 0) {
							for (Book book : pagedBookList) {
						%>

						<tr>
							<th scope="row"><%=book.getRowNum()%></th>
							<th><%=book.getTitle()%></th>
							<td><%=book.getAuthorName()%></td>
							<td><%=book.getGenre()%></td>
							<td><%=book.getLanguage()%></td>
							<td><%=book.getStatus()%></td>
							<td><%=book.getRating()%></td>
						</tr>

						<%
						}
						}
						%>

					</tbody>
				</table>

				<%

				%>
				<!-- Pagination -->
				<nav aria-label="Page navigation example">
					<ul class="pagination justify-content-end">
						<li class="page-item <%=(currentPage == 1) ? "disabled" : ""%>"><a
							class="page-link"
							href="<%=routePrefix%>Servlet?page=<%=currentPage - 1%>">Previous</a></li>
						<%
						for (int i = startPage; i <= endPage; i++) {
						%>
						<li
							class="page-item <%=(i == currentPage) ? "active" : ""%>"
							<%=(i == currentPage) ? "aria-current='page'" : ""%>><a
							class="page-link"
							href="<%=routePrefix%>Servlet?page=<%=i%>"><%=i%></a></li>

						<%
						}
						%>
						<li
							class="page-item <%=(currentPage == totalPages) ? "disabled" : ""%>"><a
							class="page-link"
							href="<%=routePrefix%>Servlet?page=<%=currentPage + 1%>">Next</a></li>
					</ul>
				</nav>
			</div>
		</div>

	</div>

	<!-- Bootstrap JS and dependencies -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>