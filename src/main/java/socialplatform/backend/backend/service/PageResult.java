package socialplatform.backend.backend.service;

import java.util.List;

public class PageResult<T> {
    private Integer currentPage;//当前页码
    private Integer pageSize;
    private Integer totalPages;//总页数
    private Long totalCount;//总记录数
    private List<T> list;

    public PageResult(Integer currentPage,Integer pageSize,Long totalCount,List<T> list)
    {
        this.currentPage=currentPage;
        this.pageSize=pageSize;
        this.totalCount=totalCount;
        this.totalPages=(int)Math.ceil((double)totalCount/pageSize);
        this.list=list;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {this.currentPage=currentPage;}
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {this.pageSize=pageSize;}
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {}
    public Long getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Long totalCount) {this.totalCount=totalCount;}
    public List<T> getList() {return list;}
    public void setList(List<T> list) {this.list=list;}

    @Override
    public String toString() {
        return "PageResult{"+
                "currentPage=" + currentPage +
                ",pageSize="+pageSize+
                ",totalCount="+totalCount+
                ",totalPages="+totalPages+
                ",list="+list+
                "}";
    }
}

