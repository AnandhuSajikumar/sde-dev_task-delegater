import { ButtonHTMLAttributes, ReactNode } from 'react';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
    variant?: 'primary' | 'outline' | 'danger';
    children: ReactNode;
    isLoading?: boolean;
}

export const Button = ({ variant = 'primary', children, isLoading, className, ...props }: ButtonProps) => {
    const baseClass = 'btn';
    let variantClass = 'btn-primary';

    if (variant === 'outline') variantClass = 'btn-outline';
    if (variant === 'danger') variantClass = 'btn-danger'; // I need to define btn-danger in css or use style

    return (
        <button
            className={`${baseClass} ${variantClass} ${className || ''}`}
            disabled={isLoading || props.disabled}
            {...props}
        >
            {isLoading ? 'Loading...' : children}
        </button>
    );
};
