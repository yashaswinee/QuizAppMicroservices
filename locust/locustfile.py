from locust import HttpUser, task, between
import json
# import logging

class QuestionServiceUser(HttpUser):
    wait_time = between(1, 3) 
    
    def on_start(self):
        self.question_count = 0
        # logging.info("Starting Question Service User...")

    @task
    def get_all_questions(self):
        """Test GET /QUESTION-SERVICE/question/allquestions"""
        with self.client.get("/QUESTION-SERVICE/question/allquestions", 
                           catch_response=True, 
                           name="Get All Questions") as response:
            if response.status_code == 200:
                try:
                    questions = response.json()
                    if isinstance(questions, list):
                        self.question_count = len(questions)
                        # logging.info(f"Successfully retrieved {len(questions)} questions")
                        
                        # Log sample question structure for verification
                        # if len(questions) > 0:
                        #     sample_question = questions[0]
                        #     logging.info(f"Sample question structure: {list(sample_question.keys())}")
                        
                        response.success()
                    else:
                        response.failure(f"Expected list, got {type(questions)}")
                        # logging.error(f"Response type error: {type(questions)}")
                except json.JSONDecodeError as e:
                    response.failure("Invalid JSON response")
                    # logging.error(f"JSON decode error: {e}")
                    # logging.error(f"Response text: {response.text[:200]}")
            else:
                response.failure(f"HTTP {response.status_code}")
                # logging.error(f"HTTP error {response.status_code}: {response.text[:200]}")

    def on_stop(self):
        """Cleanup when user stops"""
        # logging.info(f"User stopped. Last question count: {self.question_count}")


class HeavyLoadUser(HttpUser):
    
    wait_time = between(0.1, 0.5)
    
    def on_start(self):
        self.request_count = 0
        # logging.info("Starting Heavy Load User...")

    @task
    def get_all_questions_heavy(self):
        self.request_count += 1
        
        with self.client.get("/QUESTION-SERVICE/question/allquestions", 
                           catch_response=True, 
                           name="Heavy Load - Get All Questions") as response:
            if response.status_code == 200:
                try:
                    questions = response.json()
                    if isinstance(questions, list):
                        # if self.request_count % 10 == 0:  # Log every 10th request
                        #     logging.info(f"Heavy user request #{self.request_count}: {len(questions)} questions")
                        response.success()
                    else:
                        response.failure(f"Expected list, got {type(questions)}")
                except json.JSONDecodeError:
                    response.failure("Invalid JSON response")
            else:
                response.failure(f"HTTP {response.status_code}")

    def on_stop(self):
        # logging.info(f"Heavy Load User stopped after {self.request_count} requests")
        pass


class LightUser(HttpUser):
    wait_time = between(5, 10)  # Longer wait time
    
    def on_start(self):
        self.request_count = 0
        # logging.info("Starting Light User...")

    @task
    def get_all_questions_light(self):
        """Infrequent requests to /question/allquestions endpoint"""
        self.request_count += 1
        
        with self.client.get("/QUESTION-SERVICE/question/allquestions", 
                           catch_response=True, 
                           name="Light Load - Get All Questions") as response:
            if response.status_code == 200:
                try:
                    questions = response.json()
                    if isinstance(questions, list):
                        # logging.info(f"Light user request #{self.request_count}: Retrieved {len(questions)} questions")
                        
                        # Show detailed info for light users
                        # if len(questions) > 0:
                        #     sample = questions[0]
                        #     expected_fields = ['id', 'questionTitle', 'option1', 'option2', 'option3', 'option4', 'answer', 'difficulty']
                        #     actual_fields = list(sample.keys())
                        #     logging.info(f"Question fields: {actual_fields}")
                        #     missing_fields = [field for field in expected_fields if field not in actual_fields]
                        #     if missing_fields:
                        #         logging.warning(f"Missing expected fields: {missing_fields}")
                        
                        response.success()
                    else:
                        response.failure(f"Expected list, got {type(questions)}")
                        # logging.error(f"Response is not a list: {type(questions)}")
                except json.JSONDecodeError as e:
                    response.failure("Invalid JSON response")
                    # logging.error(f"JSON error: {e}")
            else:
                response.failure(f"HTTP {response.status_code}")
                # logging.error(f"HTTP {response.status_code}: {response.text[:100]}")

    def on_stop(self):
        # logging.info(f"Light User stopped after {self.request_count} requests")
        pass




# locust -f question_service_locust.py --web-port 9000 -H http://localhost:8765



# for web.py

# from locust import HttpUser, task

# class HelloWorldUser(HttpUser):
#     @task
#     def test_endpoints(self):
#         self.client.get("/hello")
#         self.client.get("/world")  
        
#     @task
#     def test_with_error_handling(self):
#         with self.client.get("/hello", catch_response=True) as response:
#             if response.status_code == 404:
#                 response.failure("Endpoint not found")
