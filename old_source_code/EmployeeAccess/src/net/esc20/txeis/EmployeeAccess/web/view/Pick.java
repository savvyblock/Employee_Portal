package net.esc20.txeis.EmployeeAccess.web.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import net.esc20.txeis.EmployeeAccess.domainobject.reference.ICode;
import net.esc20.txeis.EmployeeAccess.search.CodeCriteria;
import net.esc20.txeis.EmployeeAccess.util.Selectable;

public class Pick implements Serializable
{	
	private static final long serialVersionUID = 5869647612172094479L;
	
	private int recordsPerPage = 20;
	private int pageNumber = 1;
	private int pagesAvailable;
	private int records;
	private List<Selectable> selected = new ArrayList<Selectable>();
	private List<Selectable> paginatedSelected = new ArrayList<Selectable>();
	private boolean selectAll;
	
	private CodeCriteria codeCriteria = new CodeCriteria();
	
	private List<ICode> codes = new ArrayList<ICode>();

	public List<ICode> getCodes() {
		return codes;
	}

	public void setCodes(List<ICode> codes) {
		this.codes = codes;
	}
	
	public CodeCriteria getCodeCriteria() {
		return codeCriteria;
	}

	public void setCodeCriteria(CodeCriteria codeCriteria) {
		this.codeCriteria = codeCriteria;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	
	public List<Selectable> getSelected() {
		return selected;
	}

	public void setSelected(List<Selectable> selected) {
		this.selected = selected;
	}

	public List<Selectable> getPaginatedSelected() {
		return paginatedSelected;
	}

	public void setPaginatedSelected(List<Selectable> paginatedSelected) {
		this.paginatedSelected = paginatedSelected;
	}
	
	public void selectAll()
	{
		selectAll = true;
		for(Selectable s : selected)
		{
			s.setSelected(true);
		}
	}
	
	public void deselectAll()
	{
		selectAll = false;
		for(Selectable s : selected)
		{
			s.setSelected(false);
		}
	}



	public <T> int getPageForItem(List<T> items, T item)
	{
		if(item == null)
		{
			return 1;
		}
		
		int index = items.indexOf(item);
		
		if(index < 0)
		{
			return 1;
		}
		
		int page = (index / recordsPerPage) + 1;
		return page;
	}
	
	public void resetSelected(List<?> items)
	{
		selected = new ArrayList<Selectable>();
		
		for(int i = 0; i < items.size(); i++)
		{
			selected.add(new Selectable(false));
		}
	}
	
	public void resetPagination(List<?> items)
	{
		records = items.size();
		resetSelected(items);
		
		// Calculate total number of pages available
		pagesAvailable = (records / recordsPerPage);
		if(records % recordsPerPage != 0) {
			pagesAvailable++;
		}
		
		if(pageNumber < 1)
		{
			pageNumber = 1;
		}
		
		if (pageNumber > pagesAvailable) {
			pageNumber = pagesAvailable;
		}
		
		resetPaginatedSelectable();
	}
	
	public <T> void resetPaginationAndIndex(List<T> items, T item)
	{
		resetPagination(items);
		pageNumber = getPageForItem(items, item);
	}
	
	public <T> List<T> getPaginatedList(List<T> items)
	{
		if(items == null)
		{
			return new ArrayList<T>();
		}
		
		List<T> paginated = new ArrayList<T>();
		for(PagedItem<T> pagedItem : getPaginatedListSelectable(items)) {
			paginated.add(pagedItem.getItem());
		}
		return paginated;
	}
	
	public <T> List<PagedItem<T>> getPaginatedListSelectable(List<T> items)
	{
		if(items == null)
		{
			return new ArrayList<PagedItem<T>>();
		}
		
		List<PagedItem<T>> paginated = new ArrayList<PagedItem<T>>();
		
		int startIndex = getStartIndex();
		
		if(startIndex >= 0)
		{
			ListIterator<T> iterator = items.listIterator(getStartIndex());
			
			int pageIndex = 0;
			while (pageIndex < recordsPerPage && iterator.hasNext()) {
				paginated.add(new PagedItem<T>(iterator.next(), pageIndex + startIndex));
				pageIndex++;
			}
		}
		
		return paginated;
	}
	
	public int getStartIndex()
	{
		return (pageNumber - 1) * recordsPerPage;
	}
	
	public int getEndIndex()
	{
		return getStartIndex() + recordsPerPage;
	}
	
	public void resetPaginatedSelectable()
	{
		paginatedSelected = new ArrayList<Selectable>();
		int startIndex = getStartIndex();
		
		if(startIndex >= 0)
		{
			for(int i = startIndex; i < startIndex + recordsPerPage; i++)
			{
				if(i < selected.size())
				{
					paginatedSelected.add(selected.get(i));
				}
			}
		}
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
		resetPaginatedSelectable();
	}

	public int getPagesAvailable() {
		return pagesAvailable;
	}

	public void setPagesAvailable(int pagesAvailable) {
		this.pagesAvailable = pagesAvailable;
	}

	public int getRecords() {
		return records;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	
	
}