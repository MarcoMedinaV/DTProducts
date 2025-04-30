package cl.marcomedina.dtproducts.util;

@SuppressWarnings("unused")
public class StandardResponse {
	/* NOTE: This is an unused class, for reference only.
	 * At my current work environment, we commonly use an standard
	 * response class that allow us to return a predictable rest structure:
	 * 
	 *  {
	 *  	"status": 1
	 *  	"message": "Element created successfully",
	 *  	"data": { id: 1, name: "Cat" }
	 *  }
	*/

	private int statusCode;
	private String message;
    private Object data;

    public StandardResponse(int statusCode, String message, Object data) {
    	this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }
}