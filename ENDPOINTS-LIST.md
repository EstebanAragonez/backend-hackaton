# Lista de Endpoints

## Autenticación

### POST /api/auth/register
**Body:**
```json
{
  "username": "johndoe",
  "email": "john@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "identityDocument": "12345678",
  "dateOfBirth": "1990-01-15"
}
```

### POST /api/auth/login
**Body:**
```json
{
  "username": "johndoe",
  "password": "password123"
}
```

---

## Productos

### POST /api/products
**Headers:** `Authorization: Bearer {token}`
**Body:**
```json
{
  "name": "Producto Ejemplo",
  "description": "Descripción del producto",
  "costPrice": 50.00,
  "salePrice": 100.00,
  "stock": 100,
  "unit": "unidad",
  "image": "base64_encoded_image"
}
```

### GET /api/products
**Headers:** `Authorization: Bearer {token}`
**Query params (opcionales):**
- `activeOnly=true` (para filtrar solo productos activos)

### GET /api/products/{id}
**Headers:** `Authorization: Bearer {token}`

### PUT /api/products/{id}
**Headers:** `Authorization: Bearer {token}`
**Body:**
```json
{
  "name": "Producto Actualizado",
  "description": "Nueva descripción",
  "costPrice": 55.00,
  "salePrice": 110.00,
  "stock": 150,
  "unit": "unidad"
}
```

### PATCH /api/products/{id}/status
**Headers:** `Authorization: Bearer {token}`
**Query params:**
- `active=true` o `active=false`

### DELETE /api/products/{id}
**Headers:** `Authorization: Bearer {token}`

---

## Clientes

### POST /api/customers
**Headers:** `Authorization: Bearer {token}`
**Body:**
```json
{
  "name": "Cliente Ejemplo",
  "contactNumber": "1234567890",
  "email": "cliente@example.com"
}
```

### GET /api/customers
**Headers:** `Authorization: Bearer {token}`

---

## Transacciones (Ingresos y Egresos)

### POST /api/transactions
**Headers:** `Authorization: Bearer {token}`
**Body (VENTA - INCOME):**
```json
{
  "type": "INCOME",
  "description": "Venta de productos",
  "amount": 500.00,
  "productId": 1,
  "quantity": 5,
  "customer": {
    "name": "Cliente Nuevo",
    "contactNumber": "9876543210",
    "email": "cliente@example.com"
  },
  "transactionDate": "2025-01-15T10:30:00"
}
```

**Body (VENTA con cliente existente):**
```json
{
  "type": "INCOME",
  "description": "Venta de productos",
  "amount": 500.00,
  "productId": 1,
  "quantity": 5,
  "customerId": 1,
  "transactionDate": "2025-01-15T10:30:00"
}
```

**Body (EGRESO - EXPENSE):**
```json
{
  "type": "EXPENSE",
  "description": "Compra de materiales",
  "amount": 200.00,
  "transactionDate": "2025-01-15T10:30:00"
}
```

### GET /api/transactions
**Headers:** `Authorization: Bearer {token}`
**Query params (opcionales):**
- `type=INCOME` o `type=EXPENSE`
- `startDate=2025-01-01T00:00:00`
- `endDate=2025-01-31T23:59:59`

---

## Gastos Fijos

### POST /api/fixed-expenses
**Headers:** `Authorization: Bearer {token}`
**Body:**
```json
{
  "name": "Alquiler",
  "description": "Alquiler mensual del local",
  "amount": 1000.00,
  "frequency": "MONTHLY"
}
```
**Frecuencias válidas:** `MONTHLY`, `WEEKLY`, `YEARLY`

### GET /api/fixed-expenses
**Headers:** `Authorization: Bearer {token}`
**Query params (opcionales):**
- `activeOnly=true` (para filtrar solo gastos activos)

### PUT /api/fixed-expenses/{id}
**Headers:** `Authorization: Bearer {token}`
**Body:**
```json
{
  "name": "Alquiler Actualizado",
  "description": "Nueva descripción",
  "amount": 1200.00,
  "frequency": "MONTHLY"
}
```

### PATCH /api/fixed-expenses/{id}/status
**Headers:** `Authorization: Bearer {token}`
**Query params:**
- `active=true` o `active=false`

---

## Health Check

### GET /api/health
**Sin autenticación requerida**

