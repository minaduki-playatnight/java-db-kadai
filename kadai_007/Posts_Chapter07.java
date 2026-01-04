package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Posts_Chapter07 {
    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement statement = null;

        // ユーザーリスト
        String[][] postList = {
            { "1003","2023-02-08","昨日の夜は徹夜でした・・","13" },
            { "1002","2023-02-08","お疲れ様です！","12" },
            { "1003","2023-02-09","今日も頑張ります！","18" },
            { "1001","2023-02-09","無理は禁物ですよ！","17" },
            { "1002","2023-02-10","明日から連休ですね！","20" }
        };

        try {
            // データベースに接続
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "password"
            );

            System.out.println("データベース接続成功");

            // SQLクエリを準備
            String sqlIn = "INSERT INTO posts (user_id,posted_at,post_content,likes) VALUES (?, ?, ?, ?);";
            PreparedStatement insert_posts = con.prepareStatement(sqlIn);

            
            // リストの1行目から順番に読み込む
            int rowCnt = 0;
            for( int i = 0; i < postList.length; i++ ) {
                // SQLクエリの「?」部分をリストのデータに置き換え
            	insert_posts.setString(1, postList[i][0]); // ユーザーID
            	insert_posts.setString(2, postList[i][1]); // 投稿日時
            	insert_posts.setString(3, postList[i][2]); // 投稿内容
            	insert_posts.setString(4, postList[i][3]); // いいね数
                
                System.out.println("レコード追加:" + insert_posts.toString() );
                rowCnt = insert_posts.executeUpdate();
                System.out.println( rowCnt + "件のレコードが追加されました");
            }
            
                // セレクト
            String sqlSel = "SELECT posted_at, post_content, likes FROM posts where user_id = 1002;";
            PreparedStatement select_posts = con.prepareStatement(sqlSel);
            ResultSet result = select_posts.executeQuery();    
            
            while(result.next()) {
                	Date postedAt = result.getDate("posted_at");
                    String postContent = result.getString("post_content");
                    int likesCn = result.getInt("likes");
                    
                    System.out.println(result.getRow() + "件目：投稿日時=" + postedAt + "／投稿内容=" + postContent + "／いいね数=" + likesCn);
                
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