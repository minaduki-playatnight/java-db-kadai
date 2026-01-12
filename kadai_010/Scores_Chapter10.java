package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Scores_Chapter10 {	
	public static void main(String[] args) {
		
        Connection con = null;
        Statement statement = null;
        

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/java_db",
                "root",
                "【パスワード】"
            );

            System.out.println("データベース接続成功");

            // UPDATESQLクエリを準備
            statement = con.createStatement();
            String sql =  "UPDATE scores SET score_math =95 , score_english = 80 where id = 5;";

            // UPDATESQLクエリを実行（DBMSに送信）
            System.out.println("レコード更新:" + statement.toString() );
            int rowCnt = statement.executeUpdate(sql);
            System.out.println( rowCnt + "件のレコードが更新されました");
            
            //点数順に並べる準備
            String sql_sel = "SELECT * from scores order by score_math DESC , score_english DESC;";
            
           ResultSet result = statement.executeQuery(sql_sel);
            
           System.out.println("数学・英語の点数が高い順に並べ替えました。");
           
            //実行結果抽出
           while(result.next()) {
              	int stuId = result.getInt("id");
              	String stuName = result.getString("name");
              	int mathSco = result.getInt("score_math");
              	int engSco = result.getInt("score_english");
              	
              	System.out.println(result.getRow() + "件目：生徒ID=" + stuId + "／氏名=" + stuName + "／数学=" + mathSco + "／英語=" + engSco);
           }
            
            
        } catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            // 使用したオブジェクトを解放
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
            }
        }
    }
}