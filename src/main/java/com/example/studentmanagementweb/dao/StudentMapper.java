package com.example.studentmanagementweb.dao;

import com.example.studentmanagementweb.model.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAll();

    @Select ("SELECT * FROM student WHERE id = #{id}")
    Student findById(@Param("id") String id);

    @Insert ("INSERT INTO student (id, name, age, score) VALUES(#{id}, #{name}, #{age}, #{score})")
    int add(Student student);

    @Update("UPDATE student SET name = #{name}, age = #{age}, score = #{score} WHERE id = #{id}")
    int update(Student student);

    @Delete ("DELETE FROM student WHERE id = #{id}")
    int deleteById(@Param("id") String id);

//    private static final String FILE_NAME = "student.dat";
//    private List<Student> students = new ArrayList<>();
//
//
//    private static final String URL = "jdbc:mysql://localhost:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
//    private static final String USER = "root";
//    private static final String PASSWORD = "000000";
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch(ClassNotFoundException e) {
//            throw new RuntimeException("MySQL 驱动加载失败，请检查依赖！", e);
//        }
//    }

//    private Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//
//    public void loadData() {
//        String createTableSql = "CREATE TABLE IF NOT EXISTS student (" +
//                "id VARCHAR(20) PRIMARY KEY," +
//                "name VARCHAR(50) NOT NULL," +
//                "age INT," +
//                "score DOUBLE" +
//                ")";
//        try (Connection conn = getConnection();
//        Statement stmt = conn.createStatement()) {
//            stmt.execute(createTableSql);
//        } catch (SQLException e) {
//            System.out.println("数据库链接或者建表失败：" + e.getMessage());
//        }
//    }
//
//    public void saveData() {
//
//    }

//    public List<Student> findAll() {
//        List<Student> list = new ArrayList<>();
//        String sql = "SELECT * FROM student";
//        try (Connection conn = getConnection();
//        Statement stmt = conn.createStatement();
//        ResultSet rs = stmt.executeQuery(sql)) {
//            while (rs.next()) {
//                Student s = new Student (
//                        rs.getString("id"),
//                        rs.getString("name"),
//                        rs.getInt("age"),
//                        rs.getDouble("score")
//                );
//                list.add(s);
//            }
//        } catch (SQLException e) {
//            System.out.println("查询失败:" + e.getMessage());
//        }
//        return list;
//    }
//
//    public Student findById(String id) {
//        String sql = "SELECT * FROM student WHERE id = ?";
//        try (Connection conn = getConnection();
//        PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, id);
//            ResultSet rs = pstmt.executeQuery();
//            if (rs.next()) {
//                return new Student (
//                        rs.getString("id"),
//                        rs.getString("name"),
//                        rs.getInt("age"),
//                        rs.getDouble("score")
//                );
//            }
//        } catch  (SQLException e) {
//                System.out.println("查询单个学生失败" + e.getMessage());
//        }
//        return null;
//    }
//
//    public boolean add(Student student) {
//        String sql = "INSERT INTO student (id, name, age, score) VALUES (?, ?, ?, ?)";
//
//        try (Connection conn = getConnection();
//        PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, student.getId());
//            pstmt.setString(2, student.getName());
//            pstmt.setInt(3, student.getAge());
//            pstmt.setDouble(4, student.getScore());
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch  (SQLException e) {
//            if (e.getErrorCode() == 1062) {
//                System.out.println("学号已经存在（数据库唯一约束）");
//            } else {
//                System.out.println("添加数据失败：" + e.getMessage());
//            }
//            return false;
//        }
//    }
//
//    public boolean deleteById(String id) {
//        String sql = "DELETE FROM student WHERE id = ?";
//        try (Connection conn = getConnection();
//        PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, id);
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            System.out.println("删除数据失败：" + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean update(Student student) {
//        String sql = "UPDATE student SET name = ?, age = ?, score = ? WHERE id = ?";
//        try (Connection conn = getConnection();
//        PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1, student.getName());
//            pstmt.setInt(2, student.getAge());
//            pstmt.setDouble(3, student.getScore());
//            pstmt.setString(4, student.getId());
//
//            int affectedRows = pstmt.executeUpdate();
//            return affectedRows > 0;
//        } catch (SQLException e) {
//            System.out.println("更新数据失败：" + e.getMessage());
//            return false;
//        }
//
//    }

//    @SuppressWarnings("unchecked")
//    public void loadData()
//    {
//        File file = new File(FILE_NAME);
//        if (!file.exists()) {
//            System.out.println("未检测到数据文件，将创建新系统");
//            return ;
//        }
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//            students = (ArrayList<Student>) ois.readObject();
//        } catch (Exception e) {
//            System.out.println("数据保存失败：" + e.getMessage());
//        }
//    }

//    public void saveData() {
//        try (ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream(FILE_NAME))) {
//            oos.writeObject(students);
//        } catch (Exception e) {
//            System.out.println("数据保存失败：" + e.getMessage());
//        }
//    }

//    public List<Student> findAll() {
//        return students;
//    }

//    public Student findById(String id) {
//        for (Student s: students) {
//            if (s.getId().equals(id)) {
//                return s;
//            }
//        }
//        return null;
//    }
//    public boolean add(Student student) {
//        return students.add(student);
//    }

//    public boolean deleteById(String id) {
//        for (int i = 0; i < students.size(); i ++) {
//            if (students.get(i).getId().equals(id)) {
//                students.remove(i);
//                return true;
//            }
//        }
//        return false;
//    }

//    public boolean update(Student updateStudent) {
//        for (int i = 0; i < students.size(); i ++) {
//            if ( students.get(i).getId().equals(updateStudent.getId())) {
//                students.set(i, updateStudent);
//                return true;
//            }
//        }
//        return false;
//    }

}
