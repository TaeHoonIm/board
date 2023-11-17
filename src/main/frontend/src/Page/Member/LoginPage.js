import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import classes from './LoginPage.module.css';

export default function LoginPage(props) {
  const navigateHome = useNavigate();

  const [loginForm, setLoginForm] = useState({
    email: "defaultUser",
    password: "defaultPassword",
    loginSuccess: true,
  });

  function formChangeHandler(event) {
    const { value, name: inputValue } = event.target;
    setLoginForm({
      ...loginForm,
      [inputValue]: event.target.value.trim(),
    });
  }

  function submitHandler(event) {
    event.preventDefault();
    // api로 데이터 전송
    if (loginForm.password !== "") {
      console.log(loginForm);
    } else if (loginForm.password === "") {
      console.log("비밀번호를 입력해주세요");
    }
  }

  return (
    <>
      <div style={{ width: "100%", height: "6rem" }} />
      <div className={classes.wrapper}>
        <Container className={classes.logo}>
          <Row>
            <Col>게시판</Col>
          </Row>
        </Container>
        <Card className={classes.card}>
          <Card.Body>
            <Form onSubmit={submitHandler}>
              <Form.Group className="mb-3" controlId="formBasicEmail">
                <Form.Label>아이디</Form.Label>
                <Form.Control
                  type="text"
                  placeholder="아이디"
                  name="email"
                  onChange={formChangeHandler}
                />
              </Form.Group>

              <Form.Group className="mb-3" controlId="formBasicPassword">
                <Form.Label>비밀번호</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="비밀번호"
                  name="password"
                  onChange={formChangeHandler}
                />
                {!loginForm.loginSuccess && (
                  <div className={classes["login-guide"]}>
                    일치하는 회원정보가 없습니다
                  </div>
                )}
              </Form.Group>

              <Button
                className={classes["login_btn"]}
                variant="primary"
                type="submit"
              >
                로그인
              </Button>
            </Form>
          </Card.Body>
        </Card>
        <Container className={classes["option-wrapper"]}>
          <Row>
            <Col>
              <a href="/search-pwd" className={classes.option}>
                비밀번호 찾기
              </a>
            </Col>
            <Col>
              <a href="/search-id" className={classes.option}>
                아이디 찾기
              </a>
            </Col>
            <Col>
              <a href="/register" className={classes.option}>
                회원가입
              </a>
            </Col>
          </Row>
        </Container>
      </div>
    </>
  );
}
