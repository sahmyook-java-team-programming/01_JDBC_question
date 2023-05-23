package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class updateEmployeeInfo {

    // 수정할 사원 번호를 입력받고
    // 사원 정보(전화번호, 이메일, 부서코드, 급여, 보너스)를 입력받아 DTO객체에 담아서 update
    // update 성공하면 "직원 정보 수정에 성공하였습니다." 출력
    // update 실패하면 "직원 정보 수정에 실패하였습니다." 출력

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt = null;
        int result = 0;

        EmployeeDTO emp = new EmployeeDTO();

        Properties prop = new Properties();
        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));

            String query = prop.getProperty("updateEmployee");
            pstmt = con.prepareStatement(query);

            Scanner sc = new Scanner(System.in);

            System.out.print("수정할 사원의 번호를 입력하세요 : ");
            emp.setEmpId(sc.nextLine());

            System.out.print("수정할 사원의 휴대전화 번호를 입력하세요 : ");
            emp.setPhone(sc.nextLine());

            System.out.print("수정할 사원의 이메일을 입력하세요 : ");
            emp.setEmail(sc.nextLine());

            System.out.print("수정할 사원의 부서 코드를 입력하세요 : ");
            emp.setDeptCode(sc.nextLine());

            System.out.print("수정할 사원의 연봉을 입력하세요 : ");
            emp.setSalary(sc.nextInt());

            System.out.print("수정할 사원의 보너스를 입력하세요 : ");
            emp.setBonus(sc.nextDouble());


            pstmt.setString(1, emp.getPhone());
            pstmt.setString(2, emp.getEmail());
            pstmt.setString(3, emp.getDeptCode());
            pstmt.setInt(4, emp.getSalary());
            pstmt.setDouble(5, emp.getBonus());
            pstmt.setString(6, emp.getEmpId());

            result = pstmt.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(pstmt);
            close(con);
        }
        if(result > 0){
            System.out.println("직원 정보 수정에 성공하였습니다.");
        } else {
            System.out.println("직원 정보 수정에 실패하였습니다.");
        }
    }
}