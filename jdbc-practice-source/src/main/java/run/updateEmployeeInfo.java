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
        EmployeeDTO employeeDTO = new EmployeeDTO();

        Properties prop = new Properties();

        try {
            prop.loadFromXML(new FileInputStream("src/main/java/mapper/employee-query.xml"));
            String query = prop.getProperty("updateEmployee");

            Scanner sc = new Scanner(System.in);
            System.out.println("수정할 사원 번호를 입력하세요 : ");
            String empId = sc.nextLine();
            System.out.println("수정할 사원의 전화번호를 입력하세요 : ");
            String phone = sc.nextLine();
            System.out.println("수정할 사원의 이메일을 입력하세요 : ");
            String email = sc.nextLine();
            System.out.println("수정할 사원의 부서코드를 입력하세요 : ");
            String jobCode = sc.nextLine();
            System.out.println("수정할 사원의 급여를 입력하세요 : ");
            int salary = sc.nextInt();
            System.out.println("수정할 사원의 보너스를 입력하세요 : ");
            Double bonus = sc.nextDouble();

            employeeDTO.setEmpId(empId);
            employeeDTO.setPhone(phone);
            employeeDTO.setEmail(email);
            employeeDTO.setJobCode(jobCode);
            employeeDTO.setSalary(salary);
            employeeDTO.setBonus(bonus);

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, employeeDTO.getPhone());
            pstmt.setString(2, employeeDTO.getEmail());
            pstmt.setString(3, employeeDTO.getJobCode());
            pstmt.setInt(4, employeeDTO.getSalary());
            pstmt.setDouble(5, employeeDTO.getBonus());
            pstmt.setString(6, employeeDTO.getEmpId());

            result = pstmt.executeUpdate();

        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        } finally {
            close(con);
            close(pstmt);
        }

        if (result > 0) {
            System.out.println("직원 정보 수정에 성공하였습니다.");
            System.out.println("result = " + result);
        } else {
            System.out.println("직원 정보 수정에 실패하였습니다.");
            System.out.println("result = " + result);
        }
    }
}
