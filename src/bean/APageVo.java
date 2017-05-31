package bean;

public class APageVo {

	//페이지 분리 관련 변수 선언
	int listSize = 25; // 페이지당 표시 행수
	int blockSize = 5; // 한블럭당 표시 페이지수
	
	int totList = 0; //리스트 전체 갯수
	int totPage = 0; //전체 페이지수
	int totBlock = 0; //전체 블럭수
	
	int nowPage = 1; //현재 페이지수
	int nowBlock = 1; //현재 블럭
	
	int startNo = 0; //리스트 목록의 시작 위치
	int endNo = 0; //리스트 목록의 마지막 위치
	
	int startPage = 0; //한블럭에 표시할 시작 페이지 번호
	int endPage = 0; //한블럭에 표시할 마지막 페이지 번호
	
	public APageVo(){
		
	}
	public APageVo(int listSize, int blockSize){
		this.listSize = listSize;
		this.blockSize = blockSize;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = blockSize;
	}

	public int getTotList() {
		return totList;
	}

	public void setTotList(int totList) {
		this.totList = totList;
	}

	public int getTotPage() {
		return totPage;
	}

	public void setTotPage(int totPage) {
		this.totPage = totPage;
	}

	public int getTotBlock() {
		return totBlock;
	}

	public void setTotBlock(int totBlock) {
		this.totBlock = totBlock;
	}

	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getNowBlock() {
		return nowBlock;
	}

	public void setNowBlock(int nowBlock) {
		this.nowBlock = nowBlock;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	
}
