package cn.enjoy.core.exception;

public class BusinessException extends RuntimeException  {
	
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -2963387503320613145L;

	private String code;
	
	public static final String resCodeSplitFlat = "~";
	
	public BusinessException(){
		super();
	}
	
	public BusinessException(String message) {
		super(resCodeSplitFlat + message);
	}

	public BusinessException(String code, String message) {
		super(code + resCodeSplitFlat + message);
		this.code = code;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String code, String message, Throwable cause) {
	    super(code + resCodeSplitFlat + message , cause);
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}

