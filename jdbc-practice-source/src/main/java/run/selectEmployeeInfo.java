package run;

import model.dto.EmployeeDTO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

public class selectEmployeeInfo {

    // 사원 번호를 입력받아 해당 사원을 조회하고 DTO객체에 담아서 출력
    // 출력 구문은 DTO 객체의 toString() 내용과
    // "[이름]([부서명]) [직급명]님 환영합니다." 로 출력.

    public static void main(String[] args) {

        Connection con = getConnection();

        PreparedStatement pstmt =  null;
        ResultSet rset = null;

        EmployeeDTO employeeDTO = null;



        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("selectEmployee");
            System.out.println("query = " + query);

            Scanner sc = new Scanner(System.in);
            System.out.println("조회할 회원의 사원 번호를 입력하세요 : ");
            String empId = sc.nextLine();

            employeeDTO.setEmpId(empId);

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, employeeDTO.getEmpId());

            rset = pstmt.executeQuery();

            if (rset.next()) {
                employeeDTO = new EmployeeDTO();
                employeeDTO.setEmpName(rset.getString("EMP_NAME"));
                employeeDTO.setDeptCode(rset.getString("DEPT_CODE"));
                employeeDTO.setJobCode(rset.getString("JOB_CODE"));

                System.out.println("[" + employeeDTO.getEmpName() + "]" + "([" + employeeDTO.getDeptCode() + "])" + " [" + employeeDTO.getJobCode() + "]님 환영합니다.");

            }

        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
            close(rset);
        }
    }

}
