import java.io.*;

public class Bai18 {
	
	static int n;
	static int index = 1;
	static int[] B = {0,1,3};
	
	public static void main(String[] args) {
		int[][] A = readFile();
		sollution(A, 0, 0);
	}
	
	private static boolean checkCase(int[][] A, int x, int y, int k) {
		// nếu ô kết quả vòng đấu tại hàng x đã có chưa
		if(A[x][n] != -1)
		{
			int s = k;
			// cộng tổng giá trị các ô ko rỗng trong hàng x
			for(int i = 0; i<n; i++)
			{
				if(A[x][i] != -1) s+= A[x][i];
			}
			// kiểm tra nếu tổng các ô đó ko bằng với ô kết quả vòng loại thì trả về false
			if(y == n-1 && s != A[x][n]) return false;
			if(y < n -1 && s > A[x][n]) return false; 
		}
		// ngược lại trả lại true
		return true;
	}
	
	private static void sollution(int[][] A, int x, int y) {
		// kiểm tra x,y có vượt ra khỏi size của mảng hay chưa
		if(y == n+1)
		{
			if(x == n-1)
			{
				// nếu đã vược khỏi mảng là đã hoàn thành 1 đáp án
				showArray(A, index);
				writeFile(A, index++);
			}
			// lặp lại với vị trí dầu hàng tiếp theo
			else sollution(A, x+1, 0);
		}
		// kiểm tra A[x][y] có rỗng ko 
		else if(A[x][y] == -1)
		{
			// kiểm tra nếu A[x][y] là chứa kết quả trận đấu
			if(y < n)
			{
				for(int k = 0;k<3;k++)
					// nếu điều kiện đúng thì chấp nhập B[k] vào A[x][y]
					if(checkCase(A, x, y, B[k]))
					{
						A[x][y] = B[k];
						int s = 0, check1 = 0, check2 = 0, i = 0;
						// kiểm tra nếu hàng x đã điền đủ các ô, và chưa có kết quả vòng đấu thì tính kết quả vòng đấu
						for(; i<n; i++)
						{
							if(A[x][n] != -1 || A[x][i] == -1) break;
							s += A[x][i];
						}
						if(i == n) 
						{
							A[x][n] = s;
							check1 = 1;
						}
						
						// kiểm tra ô đối xứng với A[x][y] có rỗng ko
						if(A[y][x] == -1)
						{
							// 2 ô đối xứng qua đường chéo 0 thì có tổng là 4, hoặc 0
							if(B[k] == 0) A[y][x] = 0;
							else A[y][x] = 4 - A[x][y];
							check2 = 1;
						}
						// lặp lại với vị trí ô tiếp theo của hàng x
						sollution(A,  x, y+1);
						
						// gán lại các giá trị đã thay đổi
						if(check1 == 1) A[x][n] = -1;
						if(check2 == 1) A[y][x] = -1;
						A[x][y] = -1;
					}
			}
			// kiểm tra nếu ô đó rỗng và là ô chứa kết quả vòng đấu
			else
			{
				// tính kết quả vong đấu và gán vào
				A[x][n] = 0;
				for(int i = 0; i<n; i++)
				{
					A[x][n] += A[x][i];
				}
				sollution(A, x, y+1);
				A[x][y] = -1;
			}
		}
		// kiểm tra nếu ô đó ko rỗng, và là ô chứa kết quả trận đấu
		else if(y < n)
		{
			int check1 = 0, check2 = 0, i =0, s =0;
			// kiểm tra nếu hàng x đã điền đủ thông tin, và chưa có kết quả vòng đấu thì tính và gán vào
			for(; i<n; i++)
			{
				if(A[x][n] != -1 || A[x][i] == -1) break;
				s += A[x][i];
			}
			if(i == n) 
			{
				A[x][n] = s;
				check1 = 1;
			}
			// kiểm tra ô đối xứng với A[x][y] có rỗng ko
			if(A[y][x] == -1)
			{
				// 2 ô đối xứng qua đường chéo 0 có tổng là 4 hoặc 0
				if(A[x][y] == 0) A[y][x] = 0;
				else A[y][x] = 4 - A[x][y];
				check2 = 1;
			}
			// lặp lại với vị trí ô tiếp theo của hàng
			sollution(A, x, y+1);
			
			// gán lại các giá trị ta đã thay đổi
			if(check1 == 1) A[x][n] = -1;
			if(check2 == 1) A[y][x] = -1;
		}
		// kiểm tra nếu ô đó ko rỗng và là ô chứa kết quả vòng đấu
		else 
		{
			//gọi lại với vị trí ô tiếp theo của hàng
			sollution(A, x, y+1);
		}
	}
	
	private static void showArray(int[][] A, int k) {
		System.out.println(k);
		for(int i = 0; i<n;i++)
		{
			for(int j = 0; j<n+1; j++)
			{
				System.out.print(A[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println("---------------------------------");
	}
	
	private static void writeFile(int[][] A, int index) {
		final String UrlOut = "D:\\learnOnline\\DoAn\\NguyenCongVinh102180108\\src\\OUT.txt";
		try {
			
			File fileOUT = new File(UrlOut);
			OutputStream outputStream = new FileOutputStream(fileOUT, true);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream);
			
			int k =0;
			// mảng b lưu số dư đọc được
			int[] b = new int[50];
			// đọc số thứ tự index vào file ( trường hợp index có nhiều chữ số )
			while(index != 0)
			{
				b[k++] = index%10;
				index = index/10;
			}
			// đọc từng ký tự vào file
			for(int i = k-1;i >=0;i--)
			{
				writer.write(b[i] + 48);
			}
			k = 0;
			
			writer.write("\n");
			// đọc các giá trị khác trong mảng vào file
			for(int i = 0; i < n; i++)
			{
				for(int j = 0; j < n+1; j++)
				{	
					// nếu số có 1 chữ số ta đọc trực tiếp
					if(A[i][j] <= 0 ) writer.write(A[i][j] + 48);
					else
					{
						// số có nhiều chữ số
						int t = A[i][j];
						while(t != 0)
						{
							// chia số đó cho 10 và lưu số dư lại
							b[k++] = t%10;
							t = t/10;
						}
						// đọc từng số dư vào file theo chiều ngược lại
						for(int z = k-1;z >=0;z--)
						{
							writer.write(b[z] + 48);
						}
						k = 0;
					}
					writer.write("  ");
				}
				writer.write("\n");
			}
			
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			System.out.println("Eror : " + e.toString());
		}
	}
	
	private static int[][] readFile() {
		
		final String UrlIn = "D:\\learnOnline\\DoAn\\NguyenCongVinh102180108\\src\\INP.txt";
		int[][] A = null;
		
		try
		{
			File fileIN = new File(UrlIn);
			InputStream inputStream = new FileInputStream(fileIN);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			int c = 48, i = 0, j = 0, k = 0 , check = 0;
			// đọc số n đội bóng
			do
			{
				c = reader.read();
				if(c >= 48 && c <=57) k = k*10 + (c - 48);
			}while(c >=48 && c <=57);
			n = k; k = 0;

			// khai báo size cho mảng 2 chiều
			A = new int[n][n+1];
			
			// đọc các giá trị khác vào mảng
			while((c = reader.read()) != -1)
			{
				// đọc từng ký tự ( dạng mã ascii ) rồi ghép lại (trường hợp số có nhiều chữ số )
				if(c >=48 && c<= 57)
				{
					k = k*10 + (c - 48);
					check = 1;
				}
				// gán số đọc được vào mảng
				if(check == 1 && (c <48 || (c > 57 && c != 63))) 
				{
					A[i][j++] = k;
					k = 0;
					check = 0;
				}
				// hán ký tự ? thành -1 vào mảng
				if(c == 63) A[i][j++] = -1;
				// kiểm tra chỉ số i,j đã tràn ra khỏi size của mảng chưa
				if(j > n)
				{
					i++; 
					j = 0;
				}
				if(i > n-1) break;
			}
			reader.close();
			
		}catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		return A;
	}
}
