#!/bin/sh

NEW_ACCESS_KEY="5GrdRMB0tBjgSCuAJo0G"
NEW_SECRET_KEY="02dOxc3QE772IzSQo4IyA9WM2zWhjLFmzRAto8F7"
POLICY_NAME="dataExchangeReadwrite"
FILE_NAME="student.csv"
MINIO_ALIAS="local"
MINIO_URL="http://minio:9000"
ROOT_USER="admin"
ROOT_PASSWORD="password"

/usr/bin/mc alias set $MINIO_ALIAS $MINIO_URL $ROOT_USER $ROOT_PASSWORD --api s3v4

cat > /tmp/${POLICY_NAME}.json <<EOL
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Effect": "Allow",
            "Action": [
                "s3:GetBucketLocation",
                "s3:ListBucket"
            ],
            "Resource": [
                "arn:aws:s3:::*"
            ]
        },
        {
            "Effect": "Allow",
            "Action": [3
                "s3:GetObject",
                "s3:PutObject",
                "s3:DeleteObject"
            ],
            "Resource": [
                "arn:aws:s3:::*/*"
            ]
        }
    ]
}
EOL


/usr/bin/mc admin policy create $MINIO_ALIAS $POLICY_NAME /tmp/${POLICY_NAME}.json

/usr/bin/mc admin user add $MINIO_ALIAS $NEW_ACCESS_KEY $NEW_SECRET_KEY

/usr/bin/mc admin policy attach $MINIO_ALIAS $POLICY_NAME --user $NEW_ACCESS_KEY

/usr/bin/mc mb "$MINIO_ALIAS/sample"

/usr/bin/mc cp "$FILE_NAME" "$MINIO_ALIAS/sample/"

# Set the bucket access to public
/usr/bin/mc anonymous set public "$MINIO_ALIAS/sample/"
