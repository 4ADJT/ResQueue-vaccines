CREATE TABLE batches (
  id UUID PRIMARY KEY,
  batch_number VARCHAR(255) NOT NULL,
  vaccine_type VARCHAR(255) NOT NULL,
  manufacture_date TIMESTAMP NOT NULL,
  expiry_date TIMESTAMP NOT NULL,
  quantity INT NOT NULL,
  available_quantity INT NOT NULL
);