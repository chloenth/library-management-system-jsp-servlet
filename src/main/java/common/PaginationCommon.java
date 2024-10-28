package common;

public class PaginationCommon {
	public static int[] calculatePageBounds(int currentPage, int totalPages, int pageRange) {
		int startPage = Math.max(1, currentPage - pageRange / 2);
		int endPage = Math.min(totalPages, startPage + pageRange - 1);
		
		// Adjust startPage if endPage does not reach the pageRange
		if (endPage - startPage < pageRange - 1) {
			startPage = Math.max(1, endPage - pageRange + 1);
		}

		return new int[] { startPage, endPage };
	}
}
